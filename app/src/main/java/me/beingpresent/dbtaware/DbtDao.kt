package me.beingpresent.dbtaware

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface DbtDao {

    @Query("SELECT * from entry")
    fun getAllEntries(): List<Entry>

    @Insert()
    fun insertEntry(entry: Entry)

//    @Query("DELETE from entry")
//    fun deleteAll()
}