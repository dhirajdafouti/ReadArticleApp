package com.project.readarticleapp.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: AppCompatImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context).load(url)
                .into(view)
        }
    }

    @BindingAdapter("imageUrl")
    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = it.toUri().buildUpon()?.scheme("https")?.build()
            Picasso.with(imgView.context)
                .load(imgUri)
                .into(imgView)
        }
    }

    fun validUrl(url: String?): Boolean {
        if (url != null) {
            if (url.contains(".png") || url.contains(".jpg")) {
                return true
            }
        }
        return false
    }
}
