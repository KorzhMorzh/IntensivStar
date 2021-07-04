package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.util.setDefaultThreads

class TvShowsFragment : BaseFragment(R.layout.tv_shows_fragment) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            MovieApiClient.apiClient
                .getTvSeries()
                .setDefaultThreads()
                .subscribe { seriesResponse ->
                    val tvShowsList = seriesResponse.results.map {
                        SeriesPreviewItem(
                            it
                        ) { series -> }
                    }.toList()
                    tv_series_recycler_view.adapter = adapter.apply { addAll(tvShowsList) }
                }
        )
    }

    override fun onStop() {
        super.onStop()
        adapter.clear()
    }
}
