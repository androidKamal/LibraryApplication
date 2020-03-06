package com.androidkamallib.library.util.image

class ResolutionLimits {

    private constructor()

    private interface Portrait {
        companion object {
            const val MAX_HEIGHT = 640
        }
    }

    private interface Landscape {
        companion object {
            const val MAX_WIDTH = 640
        }
    }

    enum class ImageOrientation {
        PORTRAIT, LANDSCAPE
    }

    companion object {
        fun getImageOrientation(width: Int, height: Int): ImageOrientation? {
            val result: ImageOrientation
            result = if (width >= height) ImageOrientation.LANDSCAPE else ImageOrientation.PORTRAIT
            return result
        }
    }
}