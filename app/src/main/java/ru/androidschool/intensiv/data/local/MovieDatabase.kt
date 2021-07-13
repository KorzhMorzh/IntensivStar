package ru.androidschool.intensiv.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.data.entity.ActorEntity
import ru.androidschool.intensiv.data.entity.MovieActorCrossRef
import ru.androidschool.intensiv.data.entity.MovieEntity

@Database(entities = [MovieEntity::class, ActorEntity::class, MovieActorCrossRef::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: MovieDatabase? = null

        @Synchronized
        fun get(context: Context): MovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "MovieDatabase"
                ).build()
            }
            return instance!!
        }
    }
}
