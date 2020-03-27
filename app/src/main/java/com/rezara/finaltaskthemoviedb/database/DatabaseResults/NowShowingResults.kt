package fariborz.rezara.database.DatabaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import fariborz.rezara.database.Entities.NowShowingEntry

/**
Created by Reza*/
data class NowShowingResults(
        val data: LiveData<PagedList<NowShowingEntry>>,
        val networkErrors: LiveData<String>
)