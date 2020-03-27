package fariborz.rezara.requestmodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fariborz.rezara.models.MovieReview

/**
Created by Reza*/
class MovieReviewsRequest {
    @SerializedName("results")
    @Expose
    var reviews: List<MovieReview>? = null
}