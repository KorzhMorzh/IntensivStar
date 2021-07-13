package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.entity.MovieDetailsWithActors
import ru.androidschool.intensiv.domain.usecase.FavouriteMoviesUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailsUseCase
import ru.androidschool.intensiv.presentation.base.BaseFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.util.convertAndSetRating
import ru.androidschool.intensiv.util.load

class MovieDetailsFragment : BaseFragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val movieDetailsUseCase by lazy {
        MovieDetailsUseCase()
    }

    private val favouritesMoviesUseCase by lazy {
        FavouriteMoviesUseCase()
    }

    private lateinit var movie: MovieDetailsWithActors

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = requireArguments().getInt(FeedFragment.KEY_MOVIE_ID)

        compositeDisposable.add(
            movieDetailsUseCase
                .getMovieDetails(movieId)
                .subscribe {
                    movie = it
                    title_tv.text = it.movie.title
                    description_tv.text = it.movie.overview
                    movie_rating.convertAndSetRating(it.movie.voteAverage)
                    genre_value.text = it.movie.genres?.joinToString(", ") { genre -> genre.name }
                    studio_value.text =
                        it.movie.productionCompanies?.joinToString(", ") { company -> company.name }
                    year_value.text = it.movie.releaseDate
                    movie_image.load(it.movie.posterPath)
                    addToFavourite.isChecked = it.movie.isFavourite
                    actors_rv.adapter =
                        adapter.apply { addAll(it.actors.map { ActorPreviewItem(it) }) }
                }
        )

        back_arr.setOnClickListener {
            findNavController().popBackStack()
        }

        addToFavourite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addToFavourite() else removeFromFavourite()
        }
    }

    private fun addToFavourite() {
        favouritesMoviesUseCase.addToFavourites(movie)
    }

    private fun removeFromFavourite() {
        favouritesMoviesUseCase.removeFromFavourites(movie)
    }
}
