package fariborz.rezara.database.DatabaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import fariborz.rezara.database.Entities.PopularEntry

/**
Created by Reza*/
data class PopularResults(
        val data: LiveData<PagedList<PopularEntry>>,
        val networkErrors: LiveData<String>
)