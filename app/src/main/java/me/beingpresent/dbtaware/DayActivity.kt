package me.beingpresent.dbtaware

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View
import android.widget.ListView

class DayActivity : Activity() {

    private lateinit var listView: ListView
    private var dbtDb: DbtDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        val fab: FloatingActionButton= findViewById(R.id.fab)
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View ?) {
                startActivity(Intent(this@DayActivity, EntryActivity::class.java))
            }
        })
        listView = findViewById(R.id.entries_list_view)

        Log.d("fromToday", intent.getIntExtra("fromToday", 0).toString())
        listView.adapter = DayAdapter(this, intent.getIntExtra("fromToday", 0))

        dbtDb = DbtDatabase.getInstance(this)
        val entries : List<Entry> = dbtDb?.dao()?.getAllEntries()!!
        for(entry in entries)
            Log.d("ALLLLL.Name:", entry.dateTime.toString() + " - " + entry.name)
    }

}
