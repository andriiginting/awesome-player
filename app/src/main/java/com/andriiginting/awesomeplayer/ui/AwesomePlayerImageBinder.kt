package com.andriiginting.awesomeplayer.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.andriiginting.awesomeplayer.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class AwesomePlayerImageBinder {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["thumbnail", "error"], requireAll = false)
        fun loadImage(view: ImageView, profileImage: String?, error: Int) {
            if (!profileImage.isNullOrEmpty()) {
                Glide.with(view.context)
                    .setDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.color.colorPrimaryDark)
                            .error(R.color.exo_edit_mode_background_color))
                    .load(profileImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view)
            }
        }
    }
}