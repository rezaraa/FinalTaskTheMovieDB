package fariborz.rezara.database.DatabaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import fariborz.rezara.database.Entities.TopRatedEntry

/**
Created by Reza*/
data class TopRatedResults(
        val data: LiveData<PagedList<TopRatedEntry>>,
        val networkErrors: LiveData<String>
)