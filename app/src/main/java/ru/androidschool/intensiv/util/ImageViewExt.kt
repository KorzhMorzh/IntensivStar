package ru.androidschool.intensiv.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.load(url: String?) {
    url?.let {
        Picasso.get()
            .load(it)
            .into(this)
    }
}
