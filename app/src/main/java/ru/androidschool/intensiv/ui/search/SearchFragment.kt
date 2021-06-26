package ru.androidschool.intensiv.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.afterTextChanged
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.ui.tvshows.SeriesPreviewItem
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment(R.layout.fragment_search) {

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
        val searchTerm = requireArguments().getString(KEY_SEARCH)
        compositeDisposable.addAll(search()
            .filter { it.length > 3 }
            .map { it.replace(" ", "") }
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe { query ->
                MovieApiClient.apiClient
                    .searchMovie(query)
                    .map { it.results }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val movieList = it.map {
                            SeriesPreviewItem(
                                it
                            ) { movie -> openMovieDetails(movie) }
                        }.toList()
                        movies_recycler_view.adapter = adapter.apply { addAll(movieList) }
                    }
            })
        search_toolbar.setText(searchTerm)
    }

    private fun openMovieDetails(movie: Movie) {
        findNavController().navigate(
            R.id.movie_details_fragment,
            bundleOf(FeedFragment.KEY_MOVIE_ID to movie.id),
            options
        )
    }

    private fun search(): Observable<String> =
        Observable.create { e ->
            search_toolbar.search_edit_text.afterTextChanged {
                Timber.d(it.toString())
                e.onNext(it.toString())
            }
        }
}
