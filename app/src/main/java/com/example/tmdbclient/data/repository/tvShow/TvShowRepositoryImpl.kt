package com.example.tmdbclient.data.repository.tvShow

import com.example.tmdbclient.data.model.tvShow.TvShow
import com.example.tmdbclient.data.repository.tvShow.dataSource.TvShowCacheDataSource
import com.example.tmdbclient.data.repository.tvShow.dataSource.TvShowLocalDataSource
import com.example.tmdbclient.data.repository.tvShow.dataSource.TvShowRemoteDataSource
import com.example.tmdbclient.domain.repository.TvShowRepository

class TvShowRepositoryImpl(
    private val tvShowCacheDataSource: TvShowCacheDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) : TvShowRepository {
    override suspend fun getTvShows(): List<TvShow>? {
        return getTvShowsFromCache()
    }

    override suspend fun updateTvShows(): List<TvShow>? {
        val newTvShows = getTvShowsFromAPI()
        tvShowLocalDataSource.clearAll()
        tvShowLocalDataSource.saveTvShowsToDB(newTvShows)
        tvShowCacheDataSource.saveTvShowsToCache(newTvShows)
        return newTvShows
    }

    suspend fun getTvShowsFromAPI(): List<TvShow> {
        lateinit var tvShows: List<TvShow>
        try {
            val response = tvShowRemoteDataSource.getTvShows()
            val body = response.body()
            body?.let {
                tvShows = it.tvShows
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tvShows
    }

    suspend fun getTvShowsFromDB(): List<TvShow> {
        lateinit var tvShows: List<TvShow>
        try {
            tvShows = tvShowLocalDataSource.getTvShowsFromDB()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (tvShows.isNotEmpty()) {
            return tvShows
        } else {
            tvShows = getTvShowsFromAPI()
            tvShowLocalDataSource.saveTvShowsToDB(tvShows)
        }
        return tvShows
    }

    suspend fun getTvShowsFromCache(): List<TvShow> {
        lateinit var tvShows: List<TvShow>
        try {
            tvShows = tvShowCacheDataSource.getTvShowsFromCache()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (tvShows.isNotEmpty()) {
            return tvShows
        } else {
            tvShows = getTvShowsFromDB()
            tvShowCacheDataSource.saveTvShowsToCache(tvShows)
        }
        return tvShows
    }
}