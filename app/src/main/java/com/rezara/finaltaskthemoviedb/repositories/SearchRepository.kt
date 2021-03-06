package fariborz.rezara.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import fariborz.rezara.boundaryCallbacks.SearchBoundaryCallbacks
import fariborz.rezara.database.DatabaseResults.SearchResults
import fariborz.rezara.database.LocalCache.SearchLocalCache
import fariborz.rezara.network.NetworkService

/**
Created by Reza*/
class SearchRepository(
        private val service: NetworkService,
        private val searchCache: SearchLocalCache
) {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): SearchResults {
//        lastRequestedPage = 1
//        requestAndSaveSearchData(query)
        val dataSourceFactory = searchCache.searchesByName(query)

        val boundaryCallback = SearchBoundaryCallbacks(query, service, searchCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        return SearchResults(data, networkErrors)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}