package me.beingpresent.dbtaware

import android.app.Activity
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.AppCompatRatingBar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(aactivity: Activity, llistener: View.OnClickListener)
    : PagerAdapter()
{
    private var activity: Activity = aactivity
    private var dbtDb: DbtDatabase? = null
    private var listener: View.OnClickListener = llistener



    override fun isViewFromObject(view: View, something: Any): Boolean {
        return view == something
        TODO("not implemented")
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("DBT-DateAdapter", "instantiateItem " + ((365 - position)*-1))

        val fromToday = (365 - position - 1) * -1

        val view = ScrollView(activity)
        val ll = LinearLayout(activity)
        ll.orientation = LinearLayout.VERTICAL
        view.addView(ll)

        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.add(Calendar.DAY_OF_MONTH, fromToday)
        Log.d("instantiateItem", cal.get(Calendar.DAY_OF_MONTH).toString())

        val more = Button(activity)
        more.text = SimpleDateFormat("MMM d").format(cal.time)
        more.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v : View?) {
                val intent = Intent(activity, DayActivity::class.java)
                intent.putExtra("fromToday", fromToday)
                activity.startActivity(intent)
            }
        })
        ll.addView(more)

        Log.d("instantiateItem!", getStartOfDay(fromToday).toString())


        dbtDb = DbtDatabase.getInstance(activity)
        val pojoMaxs = dbtDb?.dao()?.getEntryMaxesForDay(getStartOfDay(fromToday))!!
        for(pojo in pojoMaxs) {
            val layout = LayoutInflater.from(activity).
                    inflate(R.layout.entry_line, null, false)

            layout.findViewById<TextView>(android.R.id.text1).text = pojo.name
            val ratingBar = layout.findViewById<AppCompatRatingBar>(android.R.id.progress)
            ratingBar.rating = pojo.maxRating
            ratingBar.setIsIndicator(true)

            ll.addView(layout)
        }

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