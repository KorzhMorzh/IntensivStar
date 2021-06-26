package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.util.load

class MovieDetailsFragment : BaseFragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = requireArguments().getInt(FeedFragment.KEY_MOVIE_ID)
        compositeDisposable.add(
            MovieApiClient.apiClient
                .getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    title_tv.text = it.title
                    description_tv.text = it.overview
                    movie_rating.rating = it.rating
                    genre_value.text = it.genres?.joinToString(", ") { genre -> genre.name }
                    studio_value.text =
                        it.productionCompanies?.joinToString(", ") { company -> company.name }
                    year_value.text = it.releaseDate
                    movie_image.load(it.poster)
                }
        )

        compositeDisposable.add(
            MovieApiClient.apiClient
                .getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    actors_rv.adapter =
                        adapter.apply { addAll(it.cast.map { ActorPreviewItem(it) }) }
                }
        )
        back_arr.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
