package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MockRepository.getMovieDetails().apply {
            title_tv.text = title
            description_tv.text = description
            movie_rating.rating = rating.toFloat()
            genre_value.text = genre
            studio_value.text = studio
            year_value.text = year
            Picasso.get()
                .load(image)
                .into(movie_image)
        }

        back_arr.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
