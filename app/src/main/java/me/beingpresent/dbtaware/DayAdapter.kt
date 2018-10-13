package me.beingpresent.dbtaware

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class DayAdapter(ccontext: Context, fromToday: Int)
    : BaseAdapter()
{
    var times : List<DbtDao.TimePojo>? = null

    init {
        val dbtDb = DbtDatabase.getInstance(ccontext)

        val startOfToday = getStartOfDay(fromToday)
        val endOfToday = startOfToday+86400

        Log.d("AHHHH.Start:", "From " + startOfToday + " to " + endOfToday + " (" + fromToday + ")")

        times = dbtDb?.dao()?.getTimesForDay(startOfToday)
        for(time in times!!) {
            Log.d("AHHHH.Time:", time.toString())
        }
    }

    private val context = ccontext
    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View? = convertView
        if(view  == null ) {
            view = LayoutInflater.from(context).
                    inflate(android.R.layout.simple_list_item_2, parent, false)
        }
        Log.d("AHHHH.getView", times!![p0].time.toString())
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.time = Date(times!![p0].time.toLong()*1000)
        cal.timeZone = TimeZone.getDefault()
        view!!.findViewById<TextView>(android.R.id.text1).text = SimpleDateFormat("h:mm a").format(cal.time)
        view!!.findViewById<TextView>(android.R.id.text2).text = times!![p0].countRatings.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        Log.d("AHHHH.position", position.toString())
        return times!!.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        Log.d("AHHHH.count", "Count")
        Log.d("AHHHH.count", times!!.size.toString())
        return times!!.size
    }

    companion object {

        fun getStartOfDay(fromToday: Int): Int {
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            cal.add(Calendar.DAY_OF_MONTH, -1 * fromToday)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return (cal.timeInMillis / 1000).toInt()
        }
    }

}