package fariborz.rezara.database.Dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import fariborz.rezara.database.Entities.FavouritesEntry

/**
Created by Reza*/

@Dao
    interface FavouritesDao {

    @Query("SELECT * FROM favourites ORDER BY timeAdded DESC")
    fun loadAllFavourites(): LiveData<MutableList<FavouritesEntry>>

    @Query("SELECT * FROM favourites WHERE movieId = :id ORDER BY timeAdded")
    fun checkIfFavourite(id: Int):LiveData<MutableList<FavouritesEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favouritesEntry: FavouritesEntry)

    @Delete
    fun deleteFavourite(favouritesEntry: FavouritesEntry)

    @Query("SELECT COUNT(movieId) FROM favourites")
    fun getNumberOfRows(): Int

}