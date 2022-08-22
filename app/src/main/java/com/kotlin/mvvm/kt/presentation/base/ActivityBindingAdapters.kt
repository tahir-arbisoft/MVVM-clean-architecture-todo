package com.kotlin.mvvm.kt.presentation.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.kotlin.mvvm.BuildConfig
import com.kotlin.mvvm.kt.utility.constants.Constants
import javax.inject.Inject


class ActivityBindingAdapters @Inject constructor(val activity: Activity) {
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {

        var customHeaderUrl: GlideUrl? = null
        if (!BuildConfig.FLAVOR.equals(
                Constants.PRODUCTION_BUILD,
                ignoreCase = true
            ) && !url.isNullOrEmpty()
        ) {
            customHeaderUrl = GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader(Constants.CUSTOM_HEADER, Constants.CUSTOM_HEADER_VALUE)
                    .build()
            )
        }

        val requestOptions = RequestOptions()
//                .fallback(R.drawable.default_image)
//                .error(R.drawable.default_image)
//                .placeholder(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        if (customHeaderUrl != null) {
            Glide.with(activity).setDefaultRequestOptions(requestOptions).load(customHeaderUrl)
                .into(imageView)
        } else {
            Glide.with(activity).setDefaultRequestOptions(requestOptions).load(url)
                .into(imageView)
        }
    }

    @BindingAdapter(value = ["imageUrl", "defaultImage"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, defaultImage: Drawable? = null) {

        var customHeaderUrl: GlideUrl? = null
        if (!BuildConfig.FLAVOR.equals(
                Constants.PRODUCTION_BUILD,
                ignoreCase = true
            ) && !url.isNullOrEmpty()
        ) {
            customHeaderUrl = GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader(Constants.CUSTOM_HEADER, Constants.CUSTOM_HEADER_VALUE)
                    .build()
            )
        }
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        if (defaultImage != null)
            requestOptions.placeholder(defaultImage)
        if (customHeaderUrl != null) {
            Glide.with(activity).setDefaultRequestOptions(requestOptions).load(customHeaderUrl)
                .into(imageView)
        } else {
            Glide.with(activity).setDefaultRequestOptions(requestOptions).load(url)
                .into(imageView)
        }
    }

    @BindingAdapter("imageResource")
    fun bindImageResource(imageView: ImageView, resourceId: Int?) {
        imageView.setImageResource(resourceId!!)
    }


}