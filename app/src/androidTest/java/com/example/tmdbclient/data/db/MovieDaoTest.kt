package com.example.tmdbclient.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tmdbclient.data.model.movie.Movie
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: MovieDao
    private lateinit var database: TMDBDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TMDBDatabase::class.java
        ).build()
        dao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveMoviesTest() = runBlocking {
        val movies = listOf(
            Movie(
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
            ),
            Movie(
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
            )
        )
        dao.saveMovies(movies)
        val movieResult=dao.getMovies()
        Truth.assertThat(movieResult).isEqualTo(movies)
    }

    @Test
    fun deleteMoviesTest() = runBlocking {
        val movies = listOf(
            Movie(
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
            ),
            Movie(
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
            )
        )
        dao.saveMovies(movies)
        dao.deleteAllMovie()
        val movieResult =  dao.getMovies()
        Truth.assertThat(movieResult).isEmpty()
    }
}