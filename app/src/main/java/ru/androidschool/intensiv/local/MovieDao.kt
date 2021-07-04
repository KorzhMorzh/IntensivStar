package ru.androidschool.intensiv.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MovieDao {
    @Insert
    fun save(movie: List<MovieEntity>): Completable
    @Insert
    fun save(movie: MovieEntity): Completable
    @Delete
    fun delete(movie: MovieEntity): Completable
    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<MovieEntity>>
}