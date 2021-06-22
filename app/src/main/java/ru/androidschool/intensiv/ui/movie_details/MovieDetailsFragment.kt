package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        MockRepository.getMovieDetails().apply {
//            title_tv.text = title
//            description_tv.text = description
//            movie_rating.rating = rating.toFloat()
//            genre_value.text = genre
//            studio_value.text = studio
//            year_value.text = year
//            movie_image.load(image)
//            actors_rv.adapter = adapter.apply { addAll(actors.map { ActorPreviewItem(it) }) }
//        }

        back_arr.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
