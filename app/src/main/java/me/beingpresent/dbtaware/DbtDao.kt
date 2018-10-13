package me.beingpresent.dbtaware

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface DbtDao {

    data class MaxPojo(var name: String, var maxRating: Float)
    data class TimePojo(var time: Int, var countRatings: Int)

    @Query("SELECT * from entry")
    fun getAllEntries(): List<Entry>

    @Query("SELECT * from entry WHERE time >= :time AND time < (:time + 86400)")
    fun getEntriesForDay(time: Int): List<Entry>

    @Query("SELECT name, MAX(rating) as maxRating FROM entry WHERE time >= :time AND time < (:time + 86400) GROUP BY name ORDER BY type ASC")
    fun getEntryMaxesForDay(time: Int): List<MaxPojo>

    @Query("SELECT time, COUNT(type) as countRatings FROM entry WHERE time >= :time AND time < (:time + 86400) GROUP BY time ORDER BY type ASC")
    fun getTimesForDay(time: Int): List<TimePojo>

    @Insert()
    fun insertEntry(entry: Entry)

    @Query("DELETE from entry")
    fun deleteAll()
}