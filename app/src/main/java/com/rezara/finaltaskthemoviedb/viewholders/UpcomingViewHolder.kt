package fariborz.rezara.viewholders

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import fariborz.rezara.R
import fariborz.rezara.database.Entities.UpcomingEntry
import fariborz.rezara.interfaces.OnMovieClickListener
import fariborz.rezara.models.Movie
import fariborz.rezara.utils.Constants
import fariborz.rezara.utils.DateUtils
import fariborz.rezara.utils.Helpers.buildImageUrl
import kotlinx.android.synthetic.main.movie_single_item.view.*

/**
Created by Reza*/
class UpcomingViewHolder(itemView: View?,
                         val context: Context,
                         val listener: OnMovieClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var movieTitle: TextView
    var movieRating: RatingBar
    var movieType: TextView
    var movieReleaseDate: TextView
    var movieOverView: TextView
    var moviePoster: ImageView
    var movieDetails: LinearLayout
    private var movie: UpcomingEntry? = null

    init{
        movieTitle = itemView!!.findViewById(R.id.single_item_movie_title)
        movieRating = itemView.findViewById(R.id.single_item_movie_rating)
        movieType = itemView.findViewById(R.id.single_item_movie_type)
        movieReleaseDate = itemView.findViewById(R.id.single_item_movie_release_date)
        moviePoster = itemView.findViewById(R.id.single_item_movie_image)
        movieOverView = itemView.findViewById(R.id.single_item_movie_overview)
        movieDetails = itemView.findViewById(R.id.single_item_movie_details)

        itemView.setOnClickListener(this)

    }

    fun bindNowShowingData(movie: UpcomingEntry?, mSharedPreferences: SharedPreferences) {
        if (movie == null) {
            return
        } else {

            this.movie = movie

            movieTitle.setText(movie.title)
            movieRating.rating = movie.voteAverage!!.div(2)
            movieReleaseDate.setText("Release date: ".plus(DateUtils.getStringDate(movie.releaseDate!!)))
            movieOverView.setText(movie.overview)

            itemView.single_item_movie_type.setText("Genre: "+movie.genreString)


            if (mSharedPreferences.getBoolean(context.getString(R.string.pref_cache_data_key),true)){
                Glide.with(context).load(buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                        .transition(withCrossFade()).into(moviePoster)
            } else{
                Glide.with(context).load(buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                        .transition(withCrossFade()).into(moviePoster)
            }
        }
    }

    private fun convertEntryToMovieList(movie: UpcomingEntry): Movie {
        val passMovie = Movie()
        passMovie.id = movie.movieId
        passMovie.voteCount = movie.voteCount
        passMovie.video = movie.video
        passMovie.voteAverage = movie.voteAverage
        passMovie.title = movie.title
        passMovie.popularity = movie.popularity
        passMovie.posterPath = movie.posterPath!!
        passMovie.originalLanguage = movie.originalLanguage
        passMovie.originalTitle = movie.originalTitle
        passMovie.backdropPath = movie.backdropPath!!
        passMovie.adult = movie.adult
        passMovie.overview = movie.overview
        passMovie.releaseDate = movie.releaseDate
        passMovie.genreString = movie.genreString!!
        passMovie.contentType = Constants.CONTENT_MOVIE
        passMovie.tableName = Constants.TOP_RATED
        return passMovie
    }

    override fun onClick(p0: View?) {
        val position:Int = adapterPosition
        if (position!= RecyclerView.NO_POSITION){
            listener.onMovieClickListener(convertEntryToMovieList(movie!!))
        }
    }

}