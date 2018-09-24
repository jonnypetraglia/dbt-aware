package me.beingpresent.dbtaware

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class DayAdapter(ccontext: Context, day: Date)
    : BaseAdapter()
{
    var entries : List<Entry>? = null

    init {
        val dbtDb = DbtDatabase.getInstance(ccontext)
        Log.d("entriesAHHHH.Name:", ((getStartOfDay((day.time/1000).toInt())).toString()))
        entries = dbtDb?.dao()?.getEntriesForDay(getStartOfDay((day.time/1000).toInt()))
        for(entry in entries!!)
            Log.d("AHHHH.Name:", entry.dateTime.toString() + " - " + entry)
    }

    private val context = ccontext
    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View? = convertView
        if(view  == null ) {
            view = LayoutInflater.from(context).
                    inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        view!!.findViewById<TextView>(android.R.id.text1).text = entries!!.get(p0).name

        return view!!
    }

    override fun getItem(position: Int): Any {
        Log.d("AHHHH.position", position.toString())
        return entries!!.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        Log.d("AHHHH.count", "Count")
        Log.d("AHHHH.count", entries!!.size.toString())
        return entries!!.size
    }


    fun getStartOfDay(fromToday : Int) : Int {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.time = Date(); // compute start of the day for the timestamp
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - fromToday)
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.timeInMillis / 1000).toInt();
    }

}