package ru.androidschool.intensiv.ui.search

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_series.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.util.load

class SearchPreviewItem(
    private val content: Movie,
    private val onClick: (series: Movie) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_search_movie

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_series.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.title.text = content.title
        viewHolder.series_rating.rating = content.rating
        content.posterPath?.let { viewHolder.image_series.load(it) }
    }
}
