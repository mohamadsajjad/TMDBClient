package com.example.tmdbclient.data.repository.tvShow.dataSource

import com.example.tmdbclient.data.model.tvShow.TvShow

interface TvShowLocalDataSource {
    suspend fun getTvShowsFromDB():List<TvShow>
    suspend fun saveTvShowsToDB(tvShows:List<TvShow>)
    suspend fun clearAll()
}