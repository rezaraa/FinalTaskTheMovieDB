package fariborz.rezara.database.LocalCache

import android.arch.paging.DataSource
import fariborz.rezara.database.Dao.PopularDao
import fariborz.rezara.database.Entities.PopularEntry
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.Executor

/**
Created by Reza*/
class PopularLocalCache(
        private val popularDao: PopularDao,
        private val ioExecutor: Executor
) {

    /**
     * Insert a list of searches in the database, on a background thread.
     */
    fun insert(repos: List<PopularEntry>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            popularDao.insert(repos)
            insertFinished()
        }
    }

    fun getAllPopular(): DataSource.Factory<Int, PopularEntry> {
        return popularDao.loadAllPopular()
    }

    fun getAllItemsInPopular(): Int {
        val data  = runBlocking {
            async(CommonPool) {
                val numItems = popularDao.getNumberOfRows()
                return@async numItems
            }.await()
        }
        return data

    }

}