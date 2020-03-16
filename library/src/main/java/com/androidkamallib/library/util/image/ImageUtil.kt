package com.androidkamallib.library.util.image

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.pow


class ImageUtil {
    var imageWidth=640
    var imageHeighth=440
    var maxImageSize = 1024 * 1024 * 5L
    //Changed maxImageInMemorySize to 200000
    val maxImageInMemorySize = 2000000 // 2 Mpx

    val FULL_QUALITY = 100
    val MIN_QUALITY = FULL_QUALITY/2
    val COMPRESSION_FACTOR_STEP = MIN_QUALITY/10
    var MAX_HEIGHT = 640
    var MAX_WIDTH = 640
    var DESIRED_FILE_SIZE = maxImageSize
    constructor(){

    }

    fun processImage(imageFile:File){

        val matrix = Matrix()
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inTempStorage = ByteArray(16 * 1024)
        //options.inPurgeable = true
        //options.inInputShareable = true

        try {

            // First Part Rotate Image
            // Source Image Input Stream
            val sourceImageFileInputStream =
                FileInputStream(imageFile)

            // don't load bitmap, just find width and height
            BitmapFactory.decodeFileDescriptor(sourceImageFileInputStream.fd, null, options)
            val originalImageWidth = options.outWidth
            val originalImageHeight = options.outHeight

            // get Image Orientation
            val imageOrientation: ImageOrientation? =
                getImageOrientation(originalImageWidth, originalImageHeight)
            var scale = 1
// calculate scale to load smaller bitmap (1.2 Mpx or smaller) to save memory
            // calculate scale to load smaller bitmap (1.2 Mpx or smaller) to save memory
            while (options.outWidth * options.outHeight * (1 / scale.toDouble().pow(2.0)) >
                maxImageInMemorySize) {
                scale *= 2
            }
            options.inSampleSize = scale
            options.inJustDecodeBounds = false

            // Rotate Image in required Orientation
           var processed = when (imageOrientation) {
                ImageOrientation.LANDSCAPE -> {
                    processLandscapeImage(originalImageWidth, options.outWidth, matrix)
                }

                ImageOrientation.PORTRAIT -> {
                    processPortraitImage(originalImageHeight, options.outHeight, matrix)
                }
               else -> false
           }
            //If Orientation processed on image then apply rotation
            if(processed) {
                processRotating(imageFile, matrix)
            }
            // load scaled bitmap and create new bitmap from original with matrix translations
            val sourceImage =
                BitmapFactory.decodeStream(sourceImageFileInputStream, null, options)

            val destinationImage = Bitmap.createBitmap(
                sourceImage!!,
                0,
                0,
                options.outWidth,
                options.outHeight,
                matrix,
                true
            )
            if (imageFile.length() > DESIRED_FILE_SIZE) {
                    resizeImage(
                        imageFile,
                        destinationImage = destinationImage,
                        reduceQuality = false
                    )
            }

        }catch ( e: IOException) {

        }
    }

    /**
     * Since scaledImageWidth from scaled bitmap might be equal or less MAX_WIDTH, we have to check condition against
     * original file width.
     */
    private fun processLandscapeImage(
        originalImageWidth: Int,
        scaledImageWidth: Int,
        matrix: Matrix
    ): Boolean {
        var processed = false
        if (originalImageWidth > MAX_WIDTH) {
            val newResolutionScale =
                MAX_WIDTH.toFloat() / scaledImageWidth
            matrix.preScale(newResolutionScale, newResolutionScale)
            processed = true
        }
        return processed
    }

    /**
     * The Same as above but related to image height.
     */
    private fun processPortraitImage(originalImageHeight: Int,
         scaledImageHeight: Int,
         matrix: Matrix
    ): Boolean {
        var processed = false
        if (originalImageHeight > MAX_WIDTH) {
            val newResolutionScale =
                MAX_HEIGHT.toFloat() / scaledImageHeight
            matrix.preScale(newResolutionScale, newResolutionScale)
            processed = true
        }
        return processed
    }

    private fun processRotating(photoFile: File, matrix: Matrix ): Boolean {
        var processed = false
        val rotateDegree = determineRotateDegree(photoFile)
        if (rotateDegree > 0) {
            matrix.postRotate(rotateDegree.toFloat())
            processed = true
        }
        return processed
    }

    /**
    * Get Rotation degree of Image
     * originalFile: File
    * */
    private fun determineRotateDegree(originalFile: File): Int {
        var rotateDegree = 0
        try {
            val exif = ExifInterface(originalFile.absolutePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateDegree = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateDegree = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateDegree = 90
            }
        } catch (e: IOException) {

        }
        return rotateDegree
    }

    /**
     * Scale bitmap image by providing
     * source:Bitmap
     * newWidth:Int
     * newHeight:Int
     * */
    fun getResizedBitmap(source: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val width = source.width
        val height = source.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)
        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            source, 0, 0, width, height, matrix, false
        )
        source.recycle()
        return resizedBitmap
    }

    /**
    * Rotate bitmap image by providing
     * source:Bitmap
     * angle:Float
    * */
    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source,
            0,
            0,
            source.width,
            source.height,
            matrix,
            true
        )
    }

    enum class ImageOrientation {
        PORTRAIT, LANDSCAPE
    }

    /**
     * Get bitmap image orientation by providing
     * source:Bitmap
     * width:Int
     * height:Int
     * */

    private fun getImageOrientation(width: Int, height: Int): ImageOrientation? {
        return if (width >= height) ImageOrientation.LANDSCAPE else ImageOrientation.PORTRAIT
    }

    private fun resizeImage(imageFile :File, reduceQuality:Boolean, destinationImage:Bitmap){
        var quality: Int
        var lowestCompressionSteps: Int
        var fileSize: Long

        //Check if File size is more than max photo size then reduce quality by 5 until
// image size is less than or equal to max size (5MB)
        if (imageFile.length() > DESIRED_FILE_SIZE && reduceQuality) {
            quality = MIN_QUALITY
            lowestCompressionSteps = 5
            fileSize = maxImageSize
        } else {
            quality = FULL_QUALITY
            lowestCompressionSteps = MIN_QUALITY
            fileSize = DESIRED_FILE_SIZE
        }

        do {
            var destinationImageFileOutputStream = FileOutputStream(imageFile)
            destinationImage.compress(
                Bitmap.CompressFormat.JPEG,
                quality,
                destinationImageFileOutputStream
            )
        }while(imageFile.length() > fileSize && quality >= lowestCompressionSteps)


    }

    private fun getDesiredFileSize(imageSize: Double): Long {
        return 1024 * imageSize.toLong()
    }


}