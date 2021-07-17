package ru.androidschool.intensiv.presentation

import android.app.Application
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.presentation.di.AppComponent
import ru.androidschool.intensiv.presentation.di.AppModule
import ru.androidschool.intensiv.presentation.di.DaggerAppComponent
import timber.log.Timber

class MovieFinderApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(this)
        initDebugTools()
    }
    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var instance: MovieFinderApp? = null
            private set
    }
}
