package fariborz.rezara.repositories

import android.arch.paging.LivePagedListBuilder
import fariborz.rezara.boundaryCallbacks.NowShowingBoundaryCallbacks
import fariborz.rezara.database.DatabaseResults.NowShowingResults
import fariborz.rezara.database.LocalCache.NowShowingLocalCache
import fariborz.rezara.network.NetworkService

/**
Created by Reza*/
class NowShowingRepository(
        private val service: NetworkService,
        private val nowShowingCache: NowShowingLocalCache
) {

    fun nowShowing(region: String): NowShowingResults {
        // Get data source factory from the local cache
        val dataSourceFactory = nowShowingCache.getAllNowShowing()

        val boundaryCallback = NowShowingBoundaryCallbacks(region, service, nowShowingCache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return NowShowingResults(data, networkErrors)
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}