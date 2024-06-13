package com.example.suitmedia_mobiletest_xml.presentation.ui.utils


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(view)
}