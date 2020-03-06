package com.androidkamallib.library.util.image

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ImageUtil {
    var imageWidth=640
    var imageHeighth=440
    var maxImageSize = 1024 * 1024 * 5
    val  FULL_QUALITY = 100
    val COMPRESSION_FACTOR_STEP = 5
    var MAX_HEIGHT = 640
    var MAX_WIDTH = 640

    constructor(){

    }

    fun resizeImage(imageFile:File){
        var processed = false
        //Changed maxImageInMemorySize to 200000
        val maxImageInMemorySize = 2000000 // 2 Mpx

        val matrix = Matrix()
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inTempStorage = ByteArray(16 * 1024)
        //options.inPurgeable = true
        //options.inInputShareable = true

        try {
            val sourceImageFileInputStream =
                FileInputStream(imageFile)

            // don't load bitmap, just find width and height
            BitmapFactory.decodeFileDescriptor(sourceImageFileInputStream.fd, null, options)
            val originalImageWidth = options.outWidth
            val originalImageHeight = options.outHeight

            val imageOrientation: ResolutionLimits.ImageOrientation? =
                ResolutionLimits.getImageOrientation(originalImageWidth, originalImageHeight)
            var scale = 1

            // calculate scale to load smaller bitmap (1.2 Mpx or smaller) to save memory
            // calculate scale to load smaller bitmap (1.2 Mpx or smaller) to save memory
            while (options.outWidth * options.outHeight * (1 / Math.pow(
                    scale.toDouble(),
                    2.0
                )) >
                maxImageInMemorySize
            ) {
                scale *= 2
            }
            options.inSampleSize = scale
            options.inJustDecodeBounds = false

            // load scaled bitmap and create new bitmap from original with matrix translations
            // load scaled bitmap and create new bitmap from original with matrix translations
            val sourceImage =
                BitmapFactory.decodeStream(sourceImageFileInputStream, null, options)
            // create new bitmap only if image has been re-sized and/or rotated
            when (imageOrientation) {
                ResolutionLimits.ImageOrientation.LANDSCAPE -> processed =
                    processLandscapeImage(
                        originalImageWidth,
                        options.outWidth,
                        matrix
                    )
                ResolutionLimits.ImageOrientation.PORTRAIT -> processed =
                    processPortraitImage(
                        originalImageHeight,
                        options.outHeight,
                        matrix
                    )
            }
            processed =
                processRotating(
                    imageFile,
                    matrix
                )
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
    private fun processPortraitImage(
        originalImageHeight: Int,
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

    private fun processRotating(
        photoFile: File,
        matrix: Matrix
    ): Boolean {
        var processed = false
        val rotateDegree = determineRotateDegree(photoFile)
        if (rotateDegree > 0) {
            matrix.postRotate(rotateDegree.toFloat())
            processed = true
        }
        return processed
    }

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
}