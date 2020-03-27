package fariborz.rezara.repositories

import android.arch.paging.LivePagedListBuilder
import fariborz.rezara.boundaryCallbacks.UpcomingBoundaryCallback
import fariborz.rezara.database.DatabaseResults.UpcomingResults
import fariborz.rezara.database.LocalCache.UpcomingLocalCache
import fariborz.rezara.network.NetworkService

/**
Created by Reza*/
class UpcomingRepository(
        private val service: NetworkService,
        private val upcomingCache: UpcomingLocalCache
) {


    /**
     * Search repositories whose names match the query.
     */
    fun upcoming(region: String): UpcomingResults {
        val dataSourceFactory = upcomingCache.getAllUpcoming()

        val boundaryCallback = UpcomingBoundaryCallback(region, service, upcomingCache)
        val networkErrors = boundaryCallback.networkErrors
        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        // Get data from the local cache
        return UpcomingResults(data, networkErrors)
    }



    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}