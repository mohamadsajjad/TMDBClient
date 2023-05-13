package com.example.tmdbclient.presentation.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.repository.movie.FakeMovieRepository
import com.example.tmdbclient.domain.usecase.GetMoviesUseCase
import com.example.tmdbclient.domain.usecase.UpdateMoviesUseCase
import com.example.tmdbclient.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        val fakeMovieRepository = FakeMovieRepository()
        viewModel = MovieViewModel(
            GetMoviesUseCase(fakeMovieRepository),
            UpdateMoviesUseCase(fakeMovieRepository)
        )
    }

    @Test
    fun getMovie_returnCurrentList() {
        val movies = mutableListOf<Movie>()
        movies.add(
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
            )
        )
        movies.add(
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

        val currentList = viewModel.getMovies().getOrAwaitValue()
        Truth.assertThat(currentList).isEqualTo(movies)
    }

    @Test
    fun updateMovie_returnUpdatedList() {
        val movies = mutableListOf<Movie>()
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

        val updatedList = viewModel.updateMovies().getOrAwaitValue()
        Truth.assertThat(updatedList).isEqualTo(movies)
    }
}