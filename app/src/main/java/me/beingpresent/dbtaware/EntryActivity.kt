package me.beingpresent.dbtaware

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import java.util.*

class EntryActivity : Activity() {

    private var dbtDb: DbtDatabase? = null

    private val emotions = arrayOf("Sadness", "Anger", "Anxiety", "Shame", "Happiness")
    private val urges = arrayOf("Self-Harm", "To kill myself")
    private val actions = arrayOf("Self-Harm", "Validate Myself")
    private val mindfulnessSkills = arrayOf(
            "Wise Mind",
            "Observe: Just notice without reacting",
            "Describe: Put words on, label emotion",
            "Participate: Enter into the experience",
            "Nonjudgmental Stance",
            "One-Mindfully In The Moment",
            "Effectiveness: Focus on what works"
    )
    private val interpersonalSkills = arrayOf(
            "Clarified Priorities",
            "Objective Effectiveness (DEAR MAN)",
            "Relationship Effectiveness (GIVE)",
            "Self-respect Effectiveness (FAST)"
    )
    private val emotionRegulationSkills = arrayOf(
            "Reduced Vulnerability (PLEASE)",
            "Built MASTERy",
            "Built Positive Experiences",
            "Opposite Action"
    )
    private val distressToleranceSkills = arrayOf(
            "Distract (Wise Mind ACCEPTS)",
            "Self-soothe",
            "IMPROVE the Moment",
            "Pros and Cons",
            "TIP-temperature, intense exercise, progressive/paired relation",
            "Radical Acceptance"
    )

    private lateinit var entryContents: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        entryContents = findViewById(R.id.entryContents)

        dbtDb = DbtDatabase.getInstance(this)

        createHeader("Emotions")
        for(emotion in emotions) {
            createRating(emotion)
        }

        createHeader("Urges")
        for(urge in urges) {
            createRating(urge)
        }

        createHeader("Actions")
        for(action in actions) {
            createRating(action)
        }


        createHeader("Mindfulness Skills")
        for(skill in mindfulnessSkills)
            createSkill(skill)
        createHeader("Interpersonal Skills")
        for(skill in interpersonalSkills)
            createSkill(skill)
        createHeader("Emotional Regulation Skills")
        for(skill in emotionRegulationSkills)
            createSkill(skill)
        createHeader("Distress Tolerance Skills")
        for(skill in distressToleranceSkills)
            createSkill(skill)

        val fab : FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?) {
                var type = -1

                val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                cal.time = Date()
                val time = (cal.timeInMillis / 1000).toInt()

                Log.d("AHHHH.fab clicked", "woot " + time)
                for(i in 0..entryContents.childCount) {
                    val view : View? = entryContents.getChildAt(i)
                    var name : String?
                    var rating: Int
                    Log.d("AHHHH.fab clicked", "woot " + i)
                    if(view is LinearLayout) {
                        rating = (view.getChildAt(1) as RatingBar).rating.toInt()
                        if(rating==0)
                            continue
                        name = (view.getChildAt(0) as TextView).text as String
                    } else if(view is CheckBox){
                        if(!view.isChecked)
                            continue
                        rating = 1
                        name = view.text as String
                    } else {
                        type = minOf(type + 1, 3)
                        continue
                    }
                    insertEntry(type, name, rating, time)
                }
                this@EntryActivity.finish()
            }
        })
    }

    fun insertEntry(type: Int, name: String, rating: Int, dateTime: Int) {
        Log.d("AHHHH.insertEntry", name)
        val entry = Entry(null, type, name, rating, dateTime)
        dbtDb?.dao()?.insertEntry(entry)
    }

    fun createHeader(str: String) {
        val txt = TextView(this)
        txt.text = str
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
        entryContents.addView(txt)
    }

    fun createRating(str: String) {
        val layout = LayoutInflater.from(this).
                inflate(R.layout.entry_line, null, false)

        layout.findViewById<TextView>(android.R.id.text1).text = str

        entryContents.addView(layout)
    }


    fun createSkill(str: String) {
        val checkBox = CheckBox(this)
        checkBox.setText(str)
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        entryContents.addView(checkBox)
    }
}
