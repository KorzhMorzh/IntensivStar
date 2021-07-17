package ru.androidschool.intensiv.presentation.tvshows

import android.view.View
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.TVShowsRepositoryImpl
import ru.androidschool.intensiv.domain.usecase.TVShowsUseCase
import ru.androidschool.intensiv.presentation.base.BaseMVPFragment

class TvShowsFragment : BaseMVPFragment<TVShowsView>(R.layout.tv_shows_fragment), TVShowsView {
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

    override val presenter = TVShowsPresenter(
        this, TVShowsUseCase(
            TVShowsRepositoryImpl(MovieApiClient.apiClient)
        )
    )

    override fun onResume() {
        super.onResume()
        presenter.view = this
    }

    override fun showTVShows(series: List<SeriesPreviewItem>) {
        tv_series_recycler_view.adapter = adapter.apply { addAll(series) }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun openDetails(id: Int?) {
        id ?: return
        // todo use layout from movie details, but with another request in the next pr
    }

    override fun clear() {
        adapter.clear()
    }
}
