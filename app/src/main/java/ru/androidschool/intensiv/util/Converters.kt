package ru.androidschool.intensiv.util

fun convertRating(rating: Float?): Float = rating?.div(2) ?: 0.0f
