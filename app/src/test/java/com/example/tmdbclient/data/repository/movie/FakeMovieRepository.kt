package com.example.tmdbclient.data.repository.movie

import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.domain.repository.MovieRepository

class FakeMovieRepository :MovieRepository{

    private val movies = mutableListOf<Movie>()

    init {
        movies.add(Movie(
            true,
            "backdrop1",
            null,
            1,
            "lan1",
            "title1",
            "overview1",
            10.8,
            "posterPath1",
            "someDate1",
            "title1",
            false,
            1.1,
            20
        ))
        movies.add(Movie(
                true,
                "backdrop2",
                null,
                2,
                "lan2",
                "title2",
                "overview2",
                10.8,
                "posterPath2",
                "someDate2",
                "title2",
                false,
                1.1,
                20
            ))
    }

    override suspend fun getMovies(): List<Movie>? {
        return movies
    }

    override suspend fun updateMovies(): List<Movie>? {
        movies.clear()
        movies.add(Movie(
            true,
            "backdrop3",
            null,
            3,
            "lan3",
            "title3",
            "overview3",
            10.8,
            "posterPath3",
            "someDate3",
            "title3",
            false,
            1.1,
            20
        ))
        return movies
    }
}