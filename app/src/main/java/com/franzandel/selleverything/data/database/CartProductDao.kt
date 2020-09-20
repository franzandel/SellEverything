package com.franzandel.selleverything.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

@Dao
interface CartProductDao {
    @Query("SELECT * FROM Product")
    fun getAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)
}