package ru.androidschool.intensiv.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.domain.usecase.FavouriteMoviesUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailsUseCase
import ru.androidschool.intensiv.presentation.base.BaseViewModel
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val favouriteMoviesUseCase: FavouriteMoviesUseCase
) : BaseViewModel() {
    private val _movie = MutableLiveData<MovieDetailsWithActors>()
    private var movieId: Int? = null
    val movie: LiveData<MovieDetailsWithActors>
        get() = _movie

    fun init(movieId: Int) {
        this.movieId = movieId
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        movieId?.let { movieId ->
            compositeDisposable.add(
                movieDetailsUseCase
                    .getMovieDetails(movieId)
                    .subscribe {
                        _movie.value = it
                    }
            )
        }
    }

    fun addToFavourite() {
        movie.value?.let {
            favouriteMoviesUseCase.addToFavourites(it)
        }
    }

    fun removeFromFavourite() {
        movie.value?.let {
            favouriteMoviesUseCase.removeFromFavourites(it)
        }
    }
}