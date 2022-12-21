package com.dev.nicehash.helpers

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by Alex Gladkov on 21.06.18.
 * class helper for loading images with Picasso library
 */
class ImageLoader {

    companion object {

        fun loadImage(url: String?, imageView: ImageView?) {
            if (url == null || imageView == null || url == "") return
            Picasso.get().load(url).into(imageView)
        }
    }
}