package fariborz.rezara.requestmodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import fariborz.rezara.models.Cast
import fariborz.rezara.models.Crew

/**
Created by Reza*/
class MovieCreditRequest {
    @SerializedName("cast")
    @Expose()
    var castResult: List<Cast>? = null

    @SerializedName("crew")
    @Expose()
    var crewResult: List<Crew>? = null
}