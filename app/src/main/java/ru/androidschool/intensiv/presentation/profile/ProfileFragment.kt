package ru.androidschool.intensiv.presentation.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
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
import ru.androidschool.intensiv.data.entity.Movie
import ru.androidschool.intensiv.data.local.MovieDatabase
import ru.androidschool.intensiv.data.repository.FavouriteMoviesRepositoryImpl
import ru.androidschool.intensiv.domain.usecase.FavouriteMoviesUseCase
import ru.androidschool.intensiv.presentation.base.BaseFragment
import ru.androidschool.intensiv.presentation.feed.FeedFragment
import ru.androidschool.intensiv.presentation.search.SearchPreviewItem

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var profileTabLayoutTitles: Array<String>

    private val favouritesMoviesUseCase by lazy {
        FavouriteMoviesUseCase(
            FavouriteMoviesRepositoryImpl(
                MovieDatabase.get(requireContext()).movieDao()
            )
        )
    }

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            favourite_movies_recycler_view.isVisible = position == FAVOURITE_MOVIES_TAB
        }
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
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

            tab.text = getSpannableTitle(number, title)
        }.attach()

        loadFavouriteMovies()
    }

    private fun getSpannableTitle(number: String, title: String): SpannableString {
        return SpannableString(title).also {
            it.setSpan(
                RelativeSizeSpan(2f),
                0,
                number.count(),
                0
            )
        }
    }

    private fun loadFavouriteMovies() {
        compositeDisposable.add(
            favouritesMoviesUseCase.getFavouritesMovies().subscribe {
                tabLayout.getTabAt(FAVOURITE_MOVIES_TAB)?.text =
                    getSpannableTitle(
                        it.size.toString(),
                        getString(R.string.favourite_movies_count, it.size)
                    )
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

    companion object {
        private const val FAVOURITE_MOVIES_TAB = 0
    }
}
