package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : BaseFragment(R.layout.feed_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        movies_recycler_view.adapter = adapter

        compositeDisposable.add(
            MovieApiClient.apiClient
                .getUpcomingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val newMoviesList = listOf(
                    MainCardContainer(
                        R.string.upcoming,
                        it.results.map {
                            MovieItem(it) { movie ->
                                openMovieDetails(movie)
                            }
                        }.toList()
                    )
                )
                adapter.apply { addAll(newMoviesList) }
            }
        )

        compositeDisposable.add(MovieApiClient.apiClient
            .getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val newMoviesList = listOf(
                    MainCardContainer(
                        R.string.popular,
                        it.results.map {
                            MovieItem(it) { movie ->
                                openMovieDetails(movie)
                            }
                        }.toList()
                    )
                )
                adapter.apply { addAll(newMoviesList) }
            }
        )

        compositeDisposable.add(MovieApiClient.apiClient
            .getPlayingNowMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val newMoviesList = listOf(
                    MainCardContainer(
                        R.string.playing_now,
                        it.results.map {
                            MovieItem(it) { movie ->
                                openMovieDetails(movie)
                            }
                        }.toList()
                    )
                )
                adapter.apply { addAll(newMoviesList) }
            }
        )
    }

    private fun openMovieDetails(movie: Movie) {
        findNavController().navigate(
            R.id.movie_details_fragment,
            bundleOf(KEY_MOVIE_ID to movie.id),
            options
        )
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        adapter.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_MOVIE_ID = "movieId"
        const val KEY_SEARCH = "search"
    }
}
