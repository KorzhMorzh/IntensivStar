package ru.androidschool.intensiv.ui.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.local.MovieDatabase
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.search.SearchPreviewItem
import ru.androidschool.intensiv.util.setDefaultThreads

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var profileTabLayoutTitles: Array<String>

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(
                requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val movieDao by lazy {
        MovieDatabase.get(requireContext()).movieDao()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(R.drawable.ic_avatar)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_avatar)
            .into(avatar)

        profileTabLayoutTitles = resources.getStringArray(R.array.tab_titles)

        val profileAdapter = ProfileAdapter(
            this,
            profileTabLayoutTitles.size
        )
        doppelgangerViewPager.adapter = profileAdapter

        doppelgangerViewPager.registerOnPageChangeCallback(profilePageChangeCallback)

        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->

            // Выделение первой части заголовка таба
            // Название таба
            val title = profileTabLayoutTitles[position]
            // Раздеряем название на части. Первый элемент будет кол-во
            val parts = profileTabLayoutTitles[position].split(" ")
            val number = parts[0]
            val spannableStringTitle = SpannableString(title)
            spannableStringTitle.setSpan(RelativeSizeSpan(2f), 0, number.count(), 0)

            tab.text = spannableStringTitle
        }.attach()

        loadFavouriteMovies()
    }

    private fun loadFavouriteMovies() {
        compositeDisposable.add(
            movieDao.getMovies().map {
                it.map { movieAndActor ->
                    Movie(
                        id = movieAndActor.movie.id,
                        title = movieAndActor.movie.title,
                        voteAverage = movieAndActor.movie.voteAverage,
                        posterPath = movieAndActor.movie.posterPath
                    )
                }
            }.setDefaultThreads().subscribe {
                val movieList = it.map {
                    SearchPreviewItem(
                        it
                    ) { movie -> openMovieDetails(movie) }
                }.toList()
                adapter.clear()
                favourite_movies_recycler_view.adapter = adapter.apply { addAll(movieList) }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        adapter.clear()
    }

    private fun openMovieDetails(movie: Movie) {
        findNavController().navigate(
            R.id.movie_details_fragment,
            bundleOf(FeedFragment.KEY_MOVIE_ID to movie.id),
            options
        )
    }
}
