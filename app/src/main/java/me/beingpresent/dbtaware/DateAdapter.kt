package me.beingpresent.dbtaware

import android.app.Activity
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.AppCompatRatingBar
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

        val fromToday = getItem(position) as Int

        val view = ScrollView(activity)
        val ll = LinearLayout(activity)
        ll.orientation = LinearLayout.VERTICAL
        view.addView(ll)

        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        cal.add(Calendar.DAY_OF_MONTH, fromToday)

        val more = Button(activity)
        more.text = SimpleDateFormat("MMM d").format(cal.time)
        more.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v : View?) {
                val intent = Intent(activity, DayActivity::class.java)
                intent.putExtra("fromToday", fromToday)
                activity.startActivityForResult(intent, 42)
            }
        })
        ll.addView(more)
        more.setOnLongClickListener(object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                dbtDb?.dao()?.deleteAll()
                Toast.makeText(activity, "Deleted all entries", Toast.LENGTH_LONG).show()
                return true
            }
        })


        dbtDb = DbtDatabase.getInstance(activity)
        val pojoMaxs = dbtDb?.dao()?.getEntryMaxesForDay(getStartOfDay(fromToday))!!
        for(pojo in pojoMaxs) {
            val layout = LayoutInflater.from(activity).
                    inflate(R.layout.entry_line, null, false) as LinearLayout

            if(pojo.type>1) {
                layout.removeView(layout.findViewById(android.R.id.progress))
                val checkBox = CheckBox(activity)
                checkBox.setText(pojo.name)
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                checkBox.isEnabled = false
                checkBox.isChecked = true
                layout.addView(checkBox)
            } else {
                val ratingBar = layout.findViewById<AppCompatRatingBar>(android.R.id.progress)
                ratingBar.rating = pojo.maxRating
                ratingBar.setIsIndicator(true)
                layout.findViewById<TextView>(android.R.id.text1).text = pojo.name
            }

            ll.addView(layout)
        }

        container.addView(view)

        view.setOnClickListener(listener)
        return view
    }



    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View?)
    }

    fun getItem(position: Int): Any {
        return (365 - position - 1) * -1
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