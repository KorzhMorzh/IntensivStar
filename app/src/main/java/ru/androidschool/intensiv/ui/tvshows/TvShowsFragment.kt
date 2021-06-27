package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment

class TvShowsFragment : BaseFragment(R.layout.tv_shows_fragment) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            MovieApiClient.apiClient
                .getTvSeries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress_bar.visibility = View.VISIBLE }
                .doOnComplete { progress_bar.visibility = View.GONE }
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
