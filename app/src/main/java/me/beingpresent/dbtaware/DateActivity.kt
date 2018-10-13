package me.beingpresent.dbtaware

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View

class DateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)
        findViewById<ViewPager>(R.id.pager).setBackgroundColor(0x99cc00)

        val pager = findViewById<ViewPager>(R.id.pager)
        pager.adapter = DateAdapter(this, object : View.OnClickListener {
            override fun onClick(v : View?) {
                val intent = Intent(this@DateActivity, DayActivity::class.java)
                intent.putExtra("fromToday", 42)
                startActivity(intent)
            }


        })
        pager.currentItem = (pager.adapter as DateAdapter).count

    }
}
