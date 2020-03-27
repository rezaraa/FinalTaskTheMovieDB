package fariborz.rezara.requestmodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fariborz.rezara.models.Movie

/**
Created by Reza*/
class MovieRequest {
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0
    @SerializedName("page")
    @Expose
    var page: Int = 0
    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
}