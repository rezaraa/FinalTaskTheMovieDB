package fariborz.rezara.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import fariborz.rezara.models.MovieDetail
import fariborz.rezara.network.NetworkService
import fariborz.rezara.requestmodels.MovieCreditRequest
import fariborz.rezara.requestmodels.MovieReviewsRequest
import fariborz.rezara.API_KEY.Companion.TMDB_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
Created by Reza*/
class MovieDetailsRepository(){

    private val service: NetworkService = NetworkService.instance

    fun getMovieDetails(movieId: String): LiveData<MovieDetail>{

        val movieDetails:MutableLiveData<MovieDetail> = MutableLiveData<MovieDetail>()
        service.tmdbApi.getDetailMovie(movieId,TMDB_API_KEY,"videos")
                .enqueue(object : Callback<MovieDetail> {
                    override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                        Log.i("MovieDetails Error","Details and Video fetch failed")
                    }

                    override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {
                        movieDetails.value = response!!.body()
                    }
                })
        return movieDetails
    }

    fun getMovieReviews(movieId: Long): LiveData<MovieReviewsRequest>{
        val movieReview:MutableLiveData<MovieReviewsRequest> = MutableLiveData<MovieReviewsRequest>()

        service.tmdbApi.getMovieReviews(movieId,TMDB_API_KEY)
                .enqueue(object : Callback<MovieReviewsRequest> {
                    override fun onFailure(call: Call<MovieReviewsRequest>?, t: Throwable?) {
                        Log.i("MovieDetails Error","Details and Video fetch failed")
                    }

                    override fun onResponse(call: Call<MovieReviewsRequest>?, response: Response<MovieReviewsRequest>?) {
                        movieReview.value = response!!.body()
                    }
                })
        return movieReview

    }

    fun getMovieCredit(movieId: Long): LiveData<MovieCreditRequest>{

        val movieCredit:MutableLiveData<MovieCreditRequest> = MutableLiveData<MovieCreditRequest>()
        service.tmdbApi.getMovieCredits(movieId,TMDB_API_KEY)
                .enqueue(object : Callback<MovieCreditRequest> {
                    override fun onFailure(call: Call<MovieCreditRequest>?, t: Throwable?) {
                        Log.i("MovieDetails Error","Details and Video fetch failed")
                    }

                    override fun onResponse(call: Call<MovieCreditRequest>?, response: Response<MovieCreditRequest>?) {
                        movieCredit.value = response!!.body()
                    }
                })
        return movieCredit
    }

}