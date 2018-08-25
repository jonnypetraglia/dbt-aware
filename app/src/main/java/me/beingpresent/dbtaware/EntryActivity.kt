package me.beingpresent.dbtaware

import android.app.Activity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView

class EntryActivity : Activity() {

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
        entryContents = this.findViewById<LinearLayout>(R.id.entryContents)

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
    }

    fun createHeader(str: String) {
        val txt = TextView(this)
        txt.text = str
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
        entryContents.addView(txt)
    }

    fun createRating(str: String) {
        val txt = TextView(this)
        txt.text = str
        txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)

        val bar = RatingBar(this)
        bar.numStars = 5
        bar.stepSize = 1.0F

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.addView(txt)
        layout.addView(bar)
        (txt.layoutParams as LinearLayout.LayoutParams).weight = 1.0F
        (txt.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER
        entryContents.addView(layout)

//        bar.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
    }


    fun createSkill(str: String) {
        val checkBox = CheckBox(this)
        checkBox.setText(str)
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        entryContents.addView(checkBox)
    }
}
