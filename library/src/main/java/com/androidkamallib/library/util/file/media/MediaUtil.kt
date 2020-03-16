package com.androidkamallib.library.util.file.media

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

class MediaUtil {
    companion object {
        /**
         * Consolidates the file path determination functionality of the various
         * media prompts. Beginning with KitKat, the responses use a different
         * mechanism and needs a lot of special handling.
         *
         * @param ctxt
         * @param uri
         * @param pathKey
         * @return
         */
        @SuppressLint("NewApi")
        fun getPathFromUri(
            ctxt: Context,
            uri: Uri,
            pathKey: String
        ): String? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // IBTFF-3527 : Fixed crash issue for column '_data' does not exist for
                // Android N and above, get file path for Android N and above devices
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    getFilePathForN(ctxt, uri)
                } else getPath(ctxt, uri)
            } else {
                if (uri.toString().startsWith("file")) {
                    uri.toString().substring(7)
                } else {
                    val projection = arrayOf(pathKey)
                    var c: Cursor? = null
                    try {
                        c = ctxt.contentResolver.query(
                            uri, projection, null,
                            null, null
                        )
                        val column_index = c!!.getColumnIndexOrThrow(pathKey)
                        var path: String? = null
                        if (c.count > 0) {
                            c.moveToFirst()
                            path = c.getString(column_index)
                        }
                        path
                    } finally {
                        c?.close()
                    }
                }
            }
        }

        /**
         * Function to get path for Android N and above
         *
         * @param uri : of the image
         * @param context
         * @return file path
         */
        private fun getFilePathForN(
            context: Context,
            uri: Uri
        ): String? {
            val returnCursor = context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )
                ?: return null
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            val file =
                File(context.filesDir, returnCursor.getString(nameIndex))
            try {
                val inputStream =
                    context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(file)
                var read = 0
                val maxBufferSize = 1024 * 1024
                val bytesAvailable = inputStream!!.available()
                val bufferSize = Math.min(bytesAvailable, maxBufferSize)
                val buffers = ByteArray(bufferSize)
                while (inputStream.read(buffers).also { read = it } != -1) {
                    outputStream.write(buffers, 0, read)
                }
                inputStream.close()
                outputStream.close()
            } catch (e: Exception) {

            } finally {
                returnCursor.close()
            }
            return file.path
        }

        @SuppressLint("NewApi")
                /**
                 * Get a file path from a Uri. This will get the the path for Storage Access
                 * Framework Documents, as well as the _data field for the MediaStore and
                 * other file-based ContentProviders.<br></br>
                 * <br></br>
                 * Callers should check whether the path is local before assuming it
                 * represents a local file.
                 *
                 * @param context The context.
                 * @param uri The Uri to query.
                 * @see .isLocal
                 * @see .getFile
                 * @author paulburke
                 */
        fun getPath(context: Context?, uri: Uri): String? {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(
                    context,
                    uri
                )
            ) { // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return (Environment.getExternalStorageDirectory().toString() + "/"
                                + split[1])
                    }
                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(context!!, contentUri, null, emptyArray())
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs =
                        arrayOf(split[1])
                    return getDataColumn(
                        context!!, contentUri, selection,
                        selectionArgs
                    )
                }
            } else if ("content".equals(
                    uri.scheme,
                    ignoreCase = true
                )
            ) { // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                    context!!,
                    uri,
                    null,
                    emptyArray()
                )
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        /**
         * @param uri
         * The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri
                .authority
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context
         * The context.
         * @param uri
         * The Uri to query.
         * @param selection
         * (Optional) Filter used in the query.
         * @param selectionArgs
         * (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         * @author paulburke
         */
        fun getDataColumn(
            context: Context, uri: Uri?,
            selection: String?, selectionArgs: Array<String>
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(
                    uri!!, projection,
                    selection, selectionArgs, null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(column_index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        /**
         * @param uri
         * The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         * @author paulburke
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri
                .authority
        }

        /**
         * @param uri
         * The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         * @author paulburke
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri
                .authority
        }

        /**
         * @param uri
         * The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         * @author paulburke
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri
                .authority
        }
    }
}