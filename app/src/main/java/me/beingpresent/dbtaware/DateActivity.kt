package me.beingpresent.dbtaware

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.util.*

class DateActivity : AppCompatActivity() {
    lateinit var pager : ViewPager

    lateinit var mNotificationTime : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)
        findViewById<ViewPager>(R.id.pager).setBackgroundColor(0x99cc00)

        pager = findViewById<ViewPager>(R.id.pager)
        pager.adapter = DateAdapter(this, object : View.OnClickListener {
            override fun onClick(v : View?) {
                val intent = Intent(this@DateActivity, DayActivity::class.java)
                intent.putExtra("fromToday", pager.currentItem)
                startActivityForResult(intent, 42)
            }
        })
        pager.currentItem = (pager.adapter as DateAdapter).count

        mNotificationTime = Calendar.getInstance()
        mNotificationTime.add(Calendar.DAY_OF_MONTH, 1)
        mNotificationTime.set(Calendar.MINUTE, 0)
        mNotificationTime.set(Calendar.SECOND, 0)

        for(i in 9..21) {
            mNotificationTime.set(Calendar.HOUR, i)
            NotificationUtils().setNotification(mNotificationTime.timeInMillis, this@DateActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        pager.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
