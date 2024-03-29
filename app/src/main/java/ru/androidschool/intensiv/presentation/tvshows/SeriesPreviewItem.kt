package ru.androidschool.intensiv.presentation.tvshows

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_series.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.entity.Series
import ru.androidschool.intensiv.util.convertAndSetRating
import ru.androidschool.intensiv.util.load

class SeriesPreviewItem(
    private val content: Series,
    private val onClick: (series: Series) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_series

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_series.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.title.text = content.name
        viewHolder.series_rating.convertAndSetRating(content.voteAverage)
        viewHolder.image_series.load(content.posterPath)
    }
}
