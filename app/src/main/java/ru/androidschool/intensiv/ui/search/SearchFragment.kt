package ru.androidschool.intensiv.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_SEARCH
import ru.androidschool.intensiv.util.setDefaultThreads
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
        compositeDisposable.addAll(search_toolbar.search()
            .filter { it.length > MIN_SEARCH_SYMBOLS }
            .map { it.trim() }
            .debounce(SEARCH_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .flatMap {
                MovieApiClient.apiClient
                    .searchMovie(it)
                    .map { pageResponse -> pageResponse.results }
            }
            .doOnSubscribe { progress_bar.visibility = View.VISIBLE }
            .setDefaultThreads()
            .subscribe {
                val movieList = it.map {
                    SearchPreviewItem(
                        it
                    ) { movie -> openMovieDetails(movie) }
                }.toList()
                adapter.clear()
                movies_recycler_view.adapter = adapter.apply { addAll(movieList) }
                progress_bar.visibility = View.GONE
            }
        )
        search_toolbar.setText(searchTerm)
    }

    private fun openMovieDetails(movie: Movie) {
        findNavController().navigate(
            R.id.movie_details_fragment,
            bundleOf(FeedFragment.KEY_MOVIE_ID to movie.id),
            options
        )
    }

    companion object {
        const val SEARCH_TIMEOUT_MILLISECONDS = 500L
        const val MIN_SEARCH_SYMBOLS = 3
    }
}
