package com.example.tmdbclient.presentation.tvShow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbclient.data.model.tvShow.TvShow
import com.example.tmdbclient.databinding.ListItemBinding

class TvShowAdapter (private val clickEvent: (TvShow) -> Unit) :
    RecyclerView.Adapter<TvShowAdapter.MyViewHolder>() {

    private val items=ArrayList<TvShow>()

    inner class MyViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtTitle.text = items[position].name
        holder.binding.txtDesc.text = items[position].overview
        val posterUrl = "https://www.themoviedb.org/t/p/w220_and_h330_face${items[position].posterPath}"
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

    fun setList(items:List<TvShow>){
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }
}