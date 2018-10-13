package me.beingpresent.dbtaware

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class DayAdapter(ccontext: Context, fromToday: Int)
    : BaseAdapter()
{
    var entries : List<Entry>? = null

    init {
        val dbtDb = DbtDatabase.getInstance(ccontext)

        val startOfToday = getStartOfDay(fromToday)
        val endOfToday = startOfToday+86400

        Log.d("AHHHH.Start:", "From " + startOfToday + " to " + endOfToday + " (" + fromToday + ")")

        entries = dbtDb?.dao()?.getEntriesForDay(startOfToday, endOfToday)
        Log.d("AHHHH.Count", "Hmm " + entries!!.size)
        for(entry in entries!!) {
            Log.d("AHHHH.Name!:", entry.toString())
            Log.d("AHHHH.Diff!:", "" + startOfToday + "<=" + entry.dateTime + "=" + (startOfToday<=entry.dateTime) + " && " + endOfToday + ">" + entry.dateTime + "=" + (endOfToday>entry.dateTime))
        }
        Log.d("AHHHH.Count", "Hmm " + entries!!.size)
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