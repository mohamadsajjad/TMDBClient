package com.example.tmdbclient.data.repository.artist

import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.repository.artist.dataSource.ArtistCacheDataSource
import com.example.tmdbclient.data.repository.artist.dataSource.ArtistLocalDataSource
import com.example.tmdbclient.data.repository.artist.dataSource.ArtistRemoteDataSource
import com.example.tmdbclient.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistCacheDataSource: ArtistCacheDataSource
):ArtistRepository {
    override suspend fun getArtists(): List<Artist>? {
        return getArtistFromCache()
    }

    override suspend fun updateArtists(): List<Artist>? {
        val newArtistList = getArtistFromAPI()
        artistLocalDataSource.clearAll()
        artistLocalDataSource.saveArtistsToDB(newArtistList)
        artistCacheDataSource.saveArtistsToCache(newArtistList)
        return newArtistList
    }

    suspend fun getArtistFromAPI():List<Artist>{
        lateinit var artistList:List<Artist>
        try {
            val response = artistRemoteDataSource.getArtist()
            val body= response.body()
            body?.let {
                artistList = it.artists
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return artistList
    }

    suspend fun getArtistFromDB():List<Artist>{
        lateinit var artistList:List<Artist>
        try {
            artistList = artistLocalDataSource.getArtistsFromDB()
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (artistList.isNotEmpty()){
            return artistList
        }else{
            artistList = getArtistFromAPI()
            artistLocalDataSource.saveArtistsToDB(artistList)
        }
        return artistList
    }

    suspend fun getArtistFromCache():List<Artist>{
        lateinit var artistList:List<Artist>
        try {
            artistList = artistCacheDataSource.getArtistsFromCache()
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (artistList.isNotEmpty()){
            return artistList
        }else{
            artistList = getArtistFromDB()
            artistCacheDataSource.saveArtistsToCache(artistList)
        }
        return artistList
    }
}