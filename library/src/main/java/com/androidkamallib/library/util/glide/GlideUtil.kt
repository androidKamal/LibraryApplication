package com.androidkamallib.library.util.glide

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideUtil {
    companion object {
        fun loadPicWithPlaceHolder(
            imageView: ImageView,
            url: String,
            context: Context,
            placeHolder: Drawable = ColorDrawable(Color.LTGRAY)
        ) {
            Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .into(imageView)
        }
    }
}