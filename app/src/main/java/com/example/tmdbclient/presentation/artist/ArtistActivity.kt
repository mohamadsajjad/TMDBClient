package com.example.tmdbclient.presentation.artist

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
import com.example.tmdbclient.databinding.ActivityArtistBinding
import com.example.tmdbclient.presentation.di.Injector
import com.example.tmdbclient.presentation.tvShow.TvShowAdapter
import javax.inject.Inject

class ArtistActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ArtistViewModelFactory
    private lateinit var artistViewModel:ArtistViewModel
    private lateinit var binding:ActivityArtistBinding
    private lateinit var adapter: ArtistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_artist)
        (application as Injector).createArtistSubComponent().inject(this)
        artistViewModel = ViewModelProvider(this,factory)[ArtistViewModel::class.java]
        initRecyclerView()
    }


    private fun initRecyclerView() {
        adapter = ArtistAdapter {

        }
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = adapter
        displayPopularArtists()
    }

    private fun displayPopularArtists() {
        binding.progressbar.visibility = View.VISIBLE
        val response = artistViewModel.getArtist()
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
                updateArtists()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateArtists(){
        binding.progressbar.visibility = View.VISIBLE
        val response = artistViewModel.updateArtist()
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