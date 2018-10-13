package me.beingpresent.dbtaware

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(ccontext: Context, llistener: View.OnClickListener)
    : PagerAdapter()
{
    private var context: Context = ccontext
    private var dbtDb: DbtDatabase? = null
    private var listener: View.OnClickListener = llistener



    override fun isViewFromObject(view: View, something: Any): Boolean {
        return view == something
        TODO("not implemented")
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("DBT-DateAdapter", "instantiateItem")
        val view = LayoutInflater.from(context).
                inflate(android.R.layout.simple_list_item_1, container, false)
        val day = Date()
        val i = getItem(position) as Int
        day.date = day.date - i


        dbtDb = DbtDatabase.getInstance(context)
        var entries : List<Entry> = dbtDb?.dao()?.getAllEntries()!!
        for(entry in entries)
            Log.d("DBT.All:", entry.dateTime.toString() + " - " + entry.name)

        Log.d("DBT.today", (day.time/1000).toString())
        val pojoMaxs = dbtDb?.dao()?.getEntryMaxesForDay(getStartOfDay((day.time/1000).toInt()))!!
        for(pojo in pojoMaxs)
            Log.d("DBT.Today:", pojo.maxRating.toString() + " - " + pojo.type)

        view.findViewById<TextView>(android.R.id.text1).setText(SimpleDateFormat("MMM d").format(day).toString())

        container.addView(view)

        view.setOnClickListener(listener)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View?)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Day of " + getItem(position).toString()
    }

    fun getItem(position: Int): Any {
        return -1 * (position + 1)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        return 365
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