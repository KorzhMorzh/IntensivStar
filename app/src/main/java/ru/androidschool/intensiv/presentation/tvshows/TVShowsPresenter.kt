package ru.androidschool.intensiv.presentation.tvshows

import ru.androidschool.intensiv.domain.usecase.TVShowsUseCase
import ru.androidschool.intensiv.presentation.base.BasePresenter
import ru.androidschool.intensiv.presentation.base.BaseView

class TVShowsPresenter(
    override var view: TVShowsView?,
    private val tvShowsUseCase: TVShowsUseCase
) : BasePresenter<TVShowsView>() {

    private fun loadTVShows() {
        disposable.addAll(
            tvShowsUseCase
                .getTVShows()
                .doOnNext { view?.showLoading() }
                .doOnComplete { view?.hideLoading() }
                .map { seriesList ->
                    seriesList.map {
                        SeriesPreviewItem(
                            it
                        ) { series -> view?.openDetails(series.id) }
                    }
                }
                .subscribe
                {
                    view?.showTVShows(it)
                }
        )
    }

    override fun resume() {
        loadTVShows()
    }

    override fun stop() {
        view?.clear()
    }
}

interface TVShowsView : BaseView {
    fun showTVShows(series: List<SeriesPreviewItem>)
    fun showLoading()
    fun hideLoading()
    fun openDetails(id: Int?)
    fun clear()
}
