package ru.androidschool.intensiv.data.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.entity.*

interface MovieApiInterface {
    @GET("movie/now_playing")
    fun getPlayingNowMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<PageResponse<Movie>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<PageResponse<Movie>>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<PageResponse<Movie>>

    @GET("tv/popular")
    fun getTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<PageResponse<Series>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<MovieDetails>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<MovieCredits>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = RU_LANGUAGE_CODE
    ): Observable<PageResponse<Movie>>

    companion object {
        private const val RU_LANGUAGE_CODE = "ru-RU"
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}
