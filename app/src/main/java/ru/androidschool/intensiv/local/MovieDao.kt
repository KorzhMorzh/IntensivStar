package ru.androidschool.intensiv.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import ru.androidschool.intensiv.util.setDefaultThreads

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(movie: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(movie: List<Actor>): Completable

    fun save(movieAndActor: MovieAndActor): Disposable {
        return save(movieAndActor.movie)
            .mergeWith(save(movieAndActor.actors))
            .andThen(saveJoins(movieAndActor.actors.map {
                MovieActorCrossRef(
                    movieAndActor.movie.id,
                    it.id
                )
            }))
            .setDefaultThreads()
            .subscribe()
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveJoins(joins: List<MovieActorCrossRef>): Completable

    @Delete
    fun delete(movie: MovieEntity): Completable

    @Delete
    fun delete(joins: List<MovieActorCrossRef>): Completable

    fun delete(movieAndActor: MovieAndActor): Disposable {
        return delete(movieAndActor.movie)
            .mergeWith(delete(movieAndActor.actors.map {
                MovieActorCrossRef(
                    movieAndActor.movie.id,
                    it.id
                )
            }))
            .setDefaultThreads()
            .subscribe()
    }

    @Transaction
    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<MovieAndActor>>

    @Transaction
    @Query("SELECT * FROM Movies WHERE movieId=:id")
    fun getMovie(id: Int): Single<MovieAndActor>
}
