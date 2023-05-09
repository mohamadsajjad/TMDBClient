package com.example.tmdbclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.model.tvShow.TvShow

@Database(
    entities = [Movie::class, TvShow::class, Artist::class], version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

}