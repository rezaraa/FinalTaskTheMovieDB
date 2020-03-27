package fariborz.rezara.database.DatabaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import fariborz.rezara.database.Entities.UpcomingEntry

/**
Created by Reza*/
data class UpcomingResults(
        val data: LiveData<PagedList<UpcomingEntry>>,
        val networkErrors: LiveData<String>
)