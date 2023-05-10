package com.example.tmdbclient.presentation.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.databinding.ListItemBinding

class ArtistAdapter (private val clickEvent: (Artist) -> Unit) :
    RecyclerView.Adapter<ArtistAdapter.MyViewHolder>() {

    private val items=ArrayList<Artist>()

    inner class MyViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtTitle.text = items[position].name
        holder.binding.txtDesc.text = "Popularity:${items[position].popularity}"
        val posterUrl = "https://www.themoviedb.org/t/p/w220_and_h330_face${items[position].profilePath}"
        Glide.with(holder.binding.img.context)
            .load(posterUrl)
            .into(holder.binding.img)

        holder.binding.root.setOnClickListener {
            clickEvent(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(items:List<Artist>){
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }
}