package com.uni.lab9_10

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class Repository(private val modeldao: Dao) {
    val allmodel: Flow<List<Model>> = modeldao.getAll()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(model: Model) {
        modeldao.insert(model)
    }

    @WorkerThread
    suspend fun update(model: Model) {
        modeldao.update(model)
    }


    @WorkerThread
    suspend fun deleteall() {
        modeldao.deleteall()
    }

    @WorkerThread
    fun deleteContact(item: Int): Int {
        return modeldao.deleteitem(item)
    }

    fun getContact(model: Model): Flow<Model> {
        return modeldao.getContact(model.Nombre)
    }
}