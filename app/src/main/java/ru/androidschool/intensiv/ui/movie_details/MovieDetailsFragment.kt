package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieCredits
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.data.MovieDetailsWithActors
import ru.androidschool.intensiv.local.MovieDatabase
import ru.androidschool.intensiv.local.convertMovieEntity
import ru.androidschool.intensiv.local.convertToMovie
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.util.convertAndSetRating
import ru.androidschool.intensiv.util.load
import ru.androidschool.intensiv.util.setDefaultThreads

class MovieDetailsFragment : BaseFragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val movieDao by lazy {
        MovieDatabase.get(requireContext()).movieDao()
    }

    private lateinit var movie: MovieDetailsWithActors

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = requireArguments().getInt(FeedFragment.KEY_MOVIE_ID)
        val remoteObservable = Observable
            .zip(
                MovieApiClient.apiClient
                    .getMovieDetails(movieId),
                MovieApiClient.apiClient
                    .getMovieCredits(movieId),
                BiFunction<MovieDetails, MovieCredits, MovieDetailsWithActors> { movie, credits ->
                    MovieDetailsWithActors(
                        movie,
                        credits.cast
                    )
                }
            )

        compositeDisposable.add(
            movieDao.getMovie(movieId).map { convertToMovie(it) }
                .toObservable()
                .onErrorResumeNext(remoteObservable)
                .switchIfEmpty(remoteObservable)
                .setDefaultThreads()
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
        movieDao.save(convertMovieEntity(movie))
    }

    private fun removeFromFavourite() {
        movieDao.delete(convertMovieEntity(movie))
    }
}
