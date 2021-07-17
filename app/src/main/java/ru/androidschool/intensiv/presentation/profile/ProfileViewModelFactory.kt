package ru.androidschool.intensiv.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.intensiv.domain.usecase.FavouriteMoviesUseCase

class ProfileViewModelFactory(private val favouriteMoviesUseCase: FavouriteMoviesUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(favouriteMoviesUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}