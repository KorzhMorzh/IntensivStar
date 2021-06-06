package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowsList =
            MockRepository.getSeries().map {
                SeriesPreviewItem(
                    it
                ) { series -> }
            }.toList()

        tv_series_recycler_view.adapter = adapter.apply { addAll(tvShowsList) }
    }
}
