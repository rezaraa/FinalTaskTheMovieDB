package fariborz.rezara.repositories

import android.arch.paging.LivePagedListBuilder
import fariborz.rezara.boundaryCallbacks.TopRatedBoundaryCallbacks
import fariborz.rezara.database.DatabaseResults.TopRatedResults
import fariborz.rezara.database.LocalCache.TopRatedLocalCache
import fariborz.rezara.network.NetworkService

/**
Created by Reza*/
class TopRatedRepository(
        private val service: NetworkService,
        private val topRatedCache: TopRatedLocalCache
) {

    fun topRated(region: String): TopRatedResults {

        val dataSourceFactory = topRatedCache.getAllTopRated()

        val boundaryCallback = TopRatedBoundaryCallbacks(region, service, topRatedCache)
        val networkErrors = boundaryCallback.networkErrors
        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return TopRatedResults(data, networkErrors)
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }


}