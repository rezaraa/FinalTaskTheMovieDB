package fariborz.rezara.adapters

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fariborz.rezara.R
import fariborz.rezara.database.Entities.FavouritesEntry
import fariborz.rezara.interfaces.OnMovieClickListener
import fariborz.rezara.viewholders.FavouritesViewHolder

/**
Created by Reza*/
class FavouritesAdapter(private val listener: OnMovieClickListener,
                        private val mSharedPreferences: SharedPreferences) : android.support.v7.recyclerview.extensions.ListAdapter<FavouritesEntry,RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_single_item, parent, false)
        this.context = parent.context
        return FavouritesViewHolder(view,context,listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movie: FavouritesEntry? = getItem(position)
        if (movie != null){
            val movieViewHolder = holder as FavouritesViewHolder
            movieViewHolder.bindFavoriteData(movie,mSharedPreferences)
        } else{
            notifyItemRemoved(position)
        }
    }

    fun getMovie(position: Int): FavouritesEntry? {
        return getItem(position)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<FavouritesEntry>() {
            override fun areItemsTheSame(oldItem: FavouritesEntry, newItem: FavouritesEntry): Boolean =
                    oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: FavouritesEntry, newItem: FavouritesEntry): Boolean =
                    oldItem == newItem
        }
    }
}