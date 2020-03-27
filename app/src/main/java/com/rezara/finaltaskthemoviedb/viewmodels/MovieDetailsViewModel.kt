package fariborz.rezara.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import fariborz.rezara.repositories.MovieDetailsRepository
import fariborz.rezara.models.MovieDetail
import fariborz.rezara.requestmodels.MovieCreditRequest
import fariborz.rezara.requestmodels.MovieReviewsRequest


/**
Created by Reza*/
class MovieDetailsViewModel(private val detailsRepo: MovieDetailsRepository): ViewModel() {

    private var movieDetail: LiveData<MovieDetail>? = null
    private var movieReviews: LiveData<MovieReviewsRequest>? = null
    private var movieCredit: LiveData<MovieCreditRequest>? = null

    fun getDetails(movieId: String): LiveData<MovieDetail>{
        if (movieDetail == null){
            movieDetail = detailsRepo.getMovieDetails(movieId)
        }
        return movieDetail!!
    }

    fun getReviews(movieId: Long): LiveData<MovieReviewsRequest>{
        if (movieReviews == null){
            movieReviews = detailsRepo.getMovieReviews(movieId)
        }
        return movieReviews!!
    }

    fun getCredits(movieId: Long): LiveData<MovieCreditRequest>{
        if (movieCredit == null){
            movieCredit = detailsRepo.getMovieCredit(movieId)
        }
        return movieCredit!!
    }

}