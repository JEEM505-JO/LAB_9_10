package com.uni.lab9_10

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Model::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun modelDao(): Dao

    private class DatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE.let {
                scope.launch(Dispatchers.IO) {
                    popupmodel(it!!.modelDao())
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java, "DATA BASE INSTANCE"
                ).fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }


        suspend fun popupmodel(modeldao: Dao) {
            modeldao.deleteall()
            val model = Model( 1,"JOAQUIN", "85615699", "Amigo")
            modeldao.insert(model)
        }
    }
}