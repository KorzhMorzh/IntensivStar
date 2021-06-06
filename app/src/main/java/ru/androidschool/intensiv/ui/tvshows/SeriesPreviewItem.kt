package ru.androidschool.intensiv.ui.tvshows

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_series.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Series

class SeriesPreviewItem(
    private val content: Series,
    private val onClick: (series: Series) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_series

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_series.setOnClickListener {
            onClick.invoke(content)
        }
        viewHolder.title.text = content.title
        viewHolder.series_rating.rating = content.rating.toFloat()
        Picasso.get()
            .load(content.image)
            .into(viewHolder.image_series)
    }
}
