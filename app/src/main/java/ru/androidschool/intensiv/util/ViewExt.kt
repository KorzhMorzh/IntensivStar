package ru.androidschool.intensiv.util

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatRatingBar
import com.squareup.picasso.Picasso

fun ImageView.load(url: String?) {
    url?.let {
        Picasso.get()
            .load(convertImagePath(it))
            .into(this)
    }
}

fun AppCompatRatingBar.convertAndSetRating(rating: Float?) {
    this.rating = convertRating(rating)
}
