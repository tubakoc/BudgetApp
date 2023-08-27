package com.example.budgetapp.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.FileNotFoundException

object Extensions {

    fun Uri.getBitmapFromUri(context: Context): Bitmap? {
        try {
            val inputStream = context.contentResolver.openInputStream(this)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun ImageView.loadUrl(url: Uri?) {

        Glide.with(this.context).load(url).circleCrop().into(this)

    }
}