package me.beingpresent.dbtaware

import android.app.Activity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView

class EntryActivity : Activity() {

    private val emotions = arrayOf("Sadness", "Anger", "Anxiety", "Shame", "Happiness")
    private val urges = arrayOf("Self-Harm", "To kill myself")
    private val actions = arrayOf("Self-Harm", "Validate Myself")
    private lateinit var entryContents: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        entryContents = this.findViewById<LinearLayout>(R.id.entryContents)

        var txt = TextView(this)
        txt.text = "Emotions"
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
        entryContents.addView(txt)
        for(emotion in emotions) {
            createRow(emotion)
        }

        txt = TextView(this)
        txt.text = "Urges"
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
        entryContents.addView(txt)
        for(urge in urges) {
            createRow(urge)
        }

        txt = TextView(this)
        txt.text = "Actions"
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
        entryContents.addView(txt)
        for(action in actions) {
            createRow(action)
        }


    }

    fun createRow(str: String) {
        var txt = TextView(this)
        txt.text = str
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)

        val bar = RatingBar(this)
        bar.numStars = 5
        bar.stepSize = 1.0F

        entryContents.addView(txt)
        entryContents.addView(bar)

        txt.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        txt.gravity = View.TEXT_ALIGNMENT_CENTER

        bar.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
    }
}
