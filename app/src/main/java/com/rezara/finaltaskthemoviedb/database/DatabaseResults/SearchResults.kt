package fariborz.rezara.database.DatabaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import fariborz.rezara.database.Entities.SearchEntry

/**
Created by Reza*/
data class SearchResults(
        val data: LiveData<PagedList<SearchEntry>>,
        val networkErrors: LiveData<String>
)