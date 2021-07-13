package ru.androidschool.intensiv.presentation.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> {
    val disposable: CompositeDisposable = CompositeDisposable()
    abstract var view: T?

    abstract fun resume()
    abstract fun stop()
    fun destroy() {
        disposable.clear()
        view = null
    }
}
