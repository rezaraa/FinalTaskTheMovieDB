package fariborz.rezara.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import fariborz.rezara.repositories.TopRatedRepository
import fariborz.rezara.database.DatabaseResults.TopRatedResults
import fariborz.rezara.database.Entities.TopRatedEntry

/**
Created by Reza*/
class TopRatedViewModel(private val repository: TopRatedRepository ) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val nowShowingResult: LiveData<TopRatedResults> = Transformations.map(queryLiveData, {
        repository.topRated(it)
    })

    val topRated: LiveData<PagedList<TopRatedEntry>> = Transformations.switchMap(nowShowingResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(nowShowingResult,
            { it -> it.networkErrors })

    fun getTopRated(region: String) {
        queryLiveData.value = region
    }

}