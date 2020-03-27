package fariborz.rezara.requestmodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fariborz.rezara.models.MovieVideo

/**
Created by Reza*/
class MovieVideosRequest {
    @SerializedName("results")
    @Expose
    var videos: List<MovieVideo>? = null
}