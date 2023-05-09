package com.example.tmdbclient.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ActivityHomeBinding
import com.example.tmdbclient.presentation.artist.ArtistActivity
import com.example.tmdbclient.presentation.movie.MovieActivity
import com.example.tmdbclient.presentation.tvShow.TvShowActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        binding.btnMovie.setOnClickListener {
            val intent = Intent(this,MovieActivity::class.java)
            this.startActivity(intent)
        }
        binding.btnTvShow.setOnClickListener {
            val intent = Intent(this,TvShowActivity::class.java)
            this.startActivity(intent)
        }
        binding.btnArtist.setOnClickListener {
            val intent = Intent(this,ArtistActivity::class.java)
            this.startActivity(intent)
        }
    }
}