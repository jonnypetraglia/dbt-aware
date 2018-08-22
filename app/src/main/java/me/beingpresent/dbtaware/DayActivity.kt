package me.beingpresent.dbtaware

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ListView
import android.widget.Toast

class DayActivity : Activity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View ?) {
                Toast.makeText(this@DayActivity, "Hi!", Toast.LENGTH_SHORT).show()
            }
        })
        listView = findViewById(R.id.entries_list_view)
    }

}
