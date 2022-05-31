package com.uni.lab9_10

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM CONTACT ORDER BY Numero")
    fun getAll(): Flow<List<Model>>

    @Query("SELECT * FROM CONTACT WHERE Nombre = :name")
    fun getContact(name: String): Flow<Model>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model: Model)

    @Query("DELETE FROM CONTACT")
    suspend fun deleteall()

    @Query("DELETE FROM CONTACT WHERE Id = :item")
    fun deleteitem(item: Int) : Int

    @Update
    suspend fun update(vararg model: Model)
}