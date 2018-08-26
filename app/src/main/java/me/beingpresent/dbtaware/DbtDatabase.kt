package me.beingpresent.dbtaware

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log

@Database(entities = [Entry::class], version = 1)
abstract class DbtDatabase : RoomDatabase() {

    abstract fun dao(): DbtDao

    companion object {
        private var INSTANCE: DbtDatabase? = null

        fun getInstance(context: Context): DbtDatabase? {
            if (INSTANCE == null) {
                synchronized(DbtDatabase::class) {
                    Log.d("TAG", "HI MOM!")
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DbtDatabase::class.java, "dbt.db")
                            .allowMainThreadQueries() //TODO: Change this using a real thread -_-
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}