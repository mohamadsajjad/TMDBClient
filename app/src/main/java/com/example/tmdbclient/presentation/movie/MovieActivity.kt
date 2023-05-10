package com.example.tmdbclient.presentation.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ActivityMovieBinding
import com.example.tmdbclient.presentation.di.Injector
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: MovieViewModelFactory
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var binding : ActivityMovieBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie)
        (application as Injector).createMovieSubComponent().inject(this)
        movieViewModel = ViewModelProvider(this,factory)[MovieViewModel::class.java]
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter {

        }
        binding.recyclerView.adapter = adapter
        displayPopularMovies()
    }

    private fun displayPopularMovies(){
        binding.progressbar.visibility = View.VISIBLE
        val responseLiveData = movieViewModel.getMovies()
        responseLiveData.observe(this){
            if (!it.isNullOrEmpty()){
                adapter.setList(it)
            }else{
                Toast.makeText(applicationContext,"No data available",Toast.LENGTH_LONG).show()
            }
            binding.progressbar.visibility = View.GONE
            responseLiveData.removeObservers(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.update,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_update -> {
                updateMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateMovies(){
        binding.progressbar.visibility = View.VISIBLE
        val response = movieViewModel.updateMovies()
        response.observe(this){
            if (!it.isNullOrEmpty()){
                adapter.setList(it)
            }else{
                Toast.makeText(applicationContext,"No data available",Toast.LENGTH_LONG).show()
            }
            binding.progressbar.visibility = View.GONE
            response.removeObservers(this)
        }
    }
}