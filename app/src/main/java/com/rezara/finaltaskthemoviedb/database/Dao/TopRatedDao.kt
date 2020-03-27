package fariborz.rezara.database.Dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import fariborz.rezara.database.Entities.TopRatedEntry

/**
Created by Reza*/
@Dao
interface TopRatedDao {

    @Query("SELECT * FROM toprated ORDER BY voteCount DESC")
    fun loadAllToprated(): DataSource.Factory<Int, TopRatedEntry>

    @Query("SELECT * FROM toprated WHERE movieId = :id ORDER BY timeAdded")
    fun checkIfToprated(id: Int):Boolean

    @Insert
    fun insertToprated(topRatedEntry: TopRatedEntry)

    @Delete
    fun deleteToprated(topRatedEntry: TopRatedEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searches: List<TopRatedEntry>)

    @Query("DELETE FROM toprated")
    fun deleteAll()

    @Query("SELECT COUNT(movieId) FROM toprated")
    fun getNumberOfRows(): Int

}