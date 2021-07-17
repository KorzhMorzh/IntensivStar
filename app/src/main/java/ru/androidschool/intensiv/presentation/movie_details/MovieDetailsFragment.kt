package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.presentation.MovieFinderApp
import ru.androidschool.intensiv.presentation.base.BaseFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.util.convertAndSetRating
import ru.androidschool.intensiv.util.load
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    @Inject
    lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MovieFinderApp).component.inject(this)
        viewModel.init(requireArguments().getInt(FeedFragment.KEY_MOVIE_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()

        back_arr.setOnClickListener {
            findNavController().popBackStack()
        }

        addToFavourite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.addToFavourite() else viewModel.removeFromFavourite()
        }
    }

    private fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            title_tv.text = it.movie.title
            description_tv.text = it.movie.overview
            movie_rating.convertAndSetRating(it.movie.voteAverage)
            genre_value.text = it.movie.genres?.joinToString(", ") { genre -> genre.name }
            studio_value.text =
                it.movie.productionCompanies?.joinToString(", ") { company -> company.name }
            year_value.text = it.movie.releaseDate
            movie_image.load(it.movie.posterPath)
            addToFavourite.isChecked = it.movie.isFavourite
            actors_rv.adapter =
                adapter.apply { addAll(it.actors.map { ActorPreviewItem(it) }) }
        })
    }
}
