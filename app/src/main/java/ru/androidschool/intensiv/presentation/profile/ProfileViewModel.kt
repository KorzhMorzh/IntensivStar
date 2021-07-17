package ru.androidschool.intensiv.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.domain.usecase.FavouriteMoviesUseCase
import ru.androidschool.intensiv.presentation.base.BaseViewModel

class ProfileViewModel(
    private val favouriteMoviesUseCase: FavouriteMoviesUseCase
) : BaseViewModel() {
    private val _favouriteMovies = MutableLiveData<List<Movie>>()
    val favouriteMovies: LiveData<List<Movie>>
        get() = _favouriteMovies

    init {
        getFavouriteMovies()
    }

    private fun getFavouriteMovies() {
        compositeDisposable.add(
            favouriteMoviesUseCase.getFavouritesMovies().subscribe {
                _favouriteMovies.value = it
            }
        )
    }
}