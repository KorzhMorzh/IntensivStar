package ru.androidschool.intensiv.util

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.setDefaultThreads() =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.setDefaultThreads() =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
