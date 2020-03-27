package fariborz.rezara.repositories

import android.arch.paging.LivePagedListBuilder
import fariborz.rezara.boundaryCallbacks.PopularBoundaryCallbacks
import fariborz.rezara.database.DatabaseResults.PopularResults
import fariborz.rezara.database.LocalCache.PopularLocalCache
import fariborz.rezara.network.NetworkService

/**
Created by Reza*/
class PopularRepository(
        private val service: NetworkService,
        private val popularCache: PopularLocalCache
) {

    fun popular(region: String): PopularResults {

        val dataSourceFactory = popularCache.getAllPopular()

        val boundaryCallback = PopularBoundaryCallbacks(region, service, popularCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return PopularResults(data, networkErrors)
    }



    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}