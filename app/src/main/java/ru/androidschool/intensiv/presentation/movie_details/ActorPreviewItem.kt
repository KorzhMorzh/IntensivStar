package ru.androidschool.intensiv.presentation.movie_details

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.entity.Actor
import ru.androidschool.intensiv.util.load

class ActorPreviewItem(
    private val content: Actor
) : Item() {

    override fun getLayout() = R.layout.item_actor

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.actor_name.text = content.name
        viewHolder.actor_image_preview.load(content.profilePath)
    }
}
