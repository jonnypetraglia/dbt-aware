package me.beingpresent.dbtaware

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface DbtDao {

    data class TypeMaxPojo(var type: String, var maxRating: Float)

    @Query("SELECT * from entry")
    fun getAllEntries(): List<Entry>

    @Query("SELECT * from entry WHERE time >= :time AND time < :time2")
    fun getEntriesForDay(time: Int, time2: Int): List<Entry>

    @Query("SELECT type, MAX(rating) as maxRating FROM entry WHERE time > :time AND (:time + 86400) GROUP BY type")
    fun getEntryMaxesForDay(time: Int): List<TypeMaxPojo>

    @Insert()
    fun insertEntry(entry: Entry)

    @Query("DELETE from entry")
    fun deleteAll()
}