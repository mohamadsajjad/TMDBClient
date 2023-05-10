package com.example.tmdbclient.presentation.tvShow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ActivityTvShowBinding
import com.example.tmdbclient.presentation.di.Injector
import javax.inject.Inject

class TvShowActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: TvShowViewModelFactory
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var binding: ActivityTvShowBinding
    private lateinit var adapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show)
        (application as Injector).createTvShowSubComponent().inject(this)
        tvShowViewModel = ViewModelProvider(this,factory)[TvShowViewModel::class.java]
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = TvShowAdapter {

        }
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = adapter
        displayPopularTvShow()
    }

    private fun displayPopularTvShow() {
        binding.progressbar.visibility = View.VISIBLE
        val response = tvShowViewModel.getTvShows()
        response.observe(this){
            if (!it.isNullOrEmpty()){
                adapter.setList(it)
            }else{
                Toast.makeText(applicationContext,"No data available", Toast.LENGTH_LONG).show()
            }
            binding.progressbar.visibility = View.GONE
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
        val response = tvShowViewModel.updateTvShows()
        response.observe(this){
            if (!it.isNullOrEmpty()){
                adapter.setList(it)
            }else{
                Toast.makeText(applicationContext,"No data available", Toast.LENGTH_LONG).show()
            }
            binding.progressbar.visibility = View.GONE
        }
    }


}