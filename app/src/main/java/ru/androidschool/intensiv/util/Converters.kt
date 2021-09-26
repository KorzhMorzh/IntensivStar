package ru.androidschool.intensiv.util

import ru.androidschool.intensiv.BuildConfig

fun convertRating(rating: Float?): Float = rating?.div(2) ?: 0.0f

fun convertImagePath(image: String?): String =
    if (image?.contains(BuildConfig.BASE_URL_IMAGE) == true ||
        image?.contains(BuildConfig.BASE_URL) == true) {
        image
    } else "${BuildConfig.BASE_URL_IMAGE}$image"
