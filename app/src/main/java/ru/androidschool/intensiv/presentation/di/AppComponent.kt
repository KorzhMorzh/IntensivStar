package ru.androidschool.intensiv.presentation.di

import dagger.Component
import ru.androidschool.intensiv.presentation.MainActivity
import ru.androidschool.intensiv.presentation.MovieFinderApp
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MovieFinderApp)
    fun inject(mainActivity: MainActivity)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}