package org.d3if0063.garbageanywhere.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GarbageDb::class], version = 1, exportSchema = false)
abstract class GarbageClass : RoomDatabase() {
    abstract val dao: GarbageDao

    companion object{
        @Volatile
        private var INSTANCE: GarbageClass? = null

        fun getInstance(context: Context): GarbageClass{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GarbageClass::class.java,
                        "garbage.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}