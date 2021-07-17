package ru.androidschool.intensiv.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class BasePresenter<T> {
    abstract var view: T?
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val ioScope = CoroutineScope(Dispatchers.IO)

    protected fun launchIO(callback: suspend () -> Unit) {
        ioScope.launch {
            callback()
        }
    }

    protected fun launchUI(callback: suspend () -> Unit) {
        uiScope.launch {
            callback()
        }
    }

    abstract fun resume()
    abstract fun stop()
    fun destroy() {
        ioScope.cancel()
        uiScope.cancel()
        view = null
    }
}
