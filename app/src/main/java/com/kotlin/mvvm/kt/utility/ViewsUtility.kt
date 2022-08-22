package com.kotlin.mvvm.kt.utility

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

fun TextView.hideIfEmptyText(text: String?) {
    if (text.isNullOrBlank()) {
        this.isVisible = false
    } else {
        this.text = text
        this.isVisible = true
    }
}

fun AppCompatTextView.hideIfEmptyText(text: String?) {
    if (text.isNullOrBlank()) {
        this.isVisible = false
    } else {
        this.text = text
        this.isVisible = true
    }
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}


fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.isVisible():Boolean {
    return visibility == View.VISIBLE
}

fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

@BindingAdapter("backgroundTintColor")
fun backgroundTintColor(view: View, color:String) {
    ViewCompat.setBackgroundTintList(
            view,
            ColorStateList.valueOf(Color.parseColor(color)))
}
