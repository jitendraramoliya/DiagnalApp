package com.diagnal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diagnal.R
import com.diagnal.databinding.ItemMovieBinding
import com.diagnal.pojo.Movie


class MovieAdapter(private val appContext: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val list: MutableList<Movie> = mutableListOf()
    private val listFiltered: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setItem(appContext, listFiltered.get(position))
    }

    fun submitList(movieList: List<Movie>) {
        // Adding movie in list
        list.addAll(movieList)
        listFiltered.addAll(movieList)
        notifyDataSetChanged()
    }

    fun setSearchText(searchText: String) {
        // filter movie base on text
        listFiltered.clear()
        if (searchText.isNullOrEmpty()) {
            listFiltered.addAll(list)
        } else {
            listFiltered.addAll(list.filter { it.name!!.startsWith(searchText, ignoreCase = true) })
        }
        notifyDataSetChanged()
    }

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(appContext: Context, movieItem: Movie) {
            binding.tvName.text = movieItem.name
            binding.tvName.isSelected = true

            movieItem.poster_image?.let {// Movie Image
                Glide.with(itemView.context).load(getImage(appContext, it))
                    .placeholder(R.drawable.placeholder_for_missing_posters)
                    .error(R.drawable.placeholder_for_missing_posters)
                    .into(binding.ivMovie)
            }

        }

        private fun getImage(appContext: Context, imageName: String?): Int {
            // Getting image by dynamic id
            val movieId = imageName?.replace(".jpg", "")
            return appContext.resources.getIdentifier(movieId, "drawable", appContext.packageName)
        }
    }
}