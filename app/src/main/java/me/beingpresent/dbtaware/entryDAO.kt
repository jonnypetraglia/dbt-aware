package me.beingpresent.dbtaware

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry(@PrimaryKey(autoGenerate = true) var id : Long?,

                 @ColumnInfo(name = "type") var type: Int,       // e.g. TYPE_EMOTION
                 @ColumnInfo(name = "name") var name: String,    // e.g. Sadness / PLEASE for Skills
                 @ColumnInfo(name = "rating") var rating: Int,   // e.g. 4 / 1=true for Skills
                 @ColumnInfo(name = "time") var dateTime: Int)   // e.g. 1/2/1990 12:42 PM (as a Unix timestamp)
{
//    val TYPE_EMOTION = 0
//    val TYPE_URGE = 1
//    val TYPE_ACTION = 2
//    val TYPE_SKILL = 3

//    @Ignore
//    fun Entry(type: Int, name: String, rating: Int) {
//        this.type = type
//        this.name = name
//        this.rating = rating
//        this.dateTime = Date().time.toInt()
//    }
}