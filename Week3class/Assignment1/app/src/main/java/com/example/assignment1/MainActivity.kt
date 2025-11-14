package com.example.assignment1

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    companion object {  const val TAG = "ClimbApp" }

     lateinit var btnClimb: Button
     lateinit var btnFall: Button
     lateinit var btnReset: Button
     lateinit var txtScore: TextView
     lateinit var txtHold: TextView
     lateinit var mainLayout: ConstraintLayout

     var score = 0
     var currentHold = 0
     var hasFallen = false
     var reachedTop = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        // load saved language before anything else
        loadLocale()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // safe window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // find views
        mainLayout = findViewById(R.id.main)
        btnClimb = findViewById(R.id.btnClimb)
        btnFall = findViewById(R.id.btnFall)
        btnReset = findViewById(R.id.btnReset)
        txtScore = findViewById(R.id.txtScore)
        txtHold = findViewById(R.id.txtHold)

        // language radio group
        val languageGroup: RadioGroup = findViewById(R.id.languageGroup)
        languageGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioVietnamese -> {
                    if (getSharedPreferences("Settings", MODE_PRIVATE)
                            .getString("App_Lang", "") != "vi"
                    ) {
                        setLocale("vi")
                    }
                }
                R.id.radioEnglish -> {
                    if (getSharedPreferences("Settings", MODE_PRIVATE)
                            .getString("App_Lang", "") != "en"
                    ) {
                        setLocale("en")
                    }
                }
            }
        }

        // restore state
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score", 0)
            currentHold = savedInstanceState.getInt("hold", 0)
            hasFallen = savedInstanceState.getBoolean("fallen", false)
            reachedTop = savedInstanceState.getBoolean("reachedTop", false)
        }

        // click listeners
        btnClimb.setOnClickListener {
            if (!hasFallen && !reachedTop && currentHold < 9) {
                currentHold++
                score += when (currentHold) {
                    in 1..3 -> 1
                    in 4..6 -> 2
                    in 7..9 -> 3
                    else -> 0
                }
                if (score > 18) score = 18
                if (currentHold == 9) reachedTop = true
            }
            updateUI()
        }

        btnFall.setOnClickListener {
            if (currentHold >= 1 && !hasFallen && !reachedTop) {
                score = maxOf(0, score - 3)
                hasFallen = true
            }
            updateUI()
        }

        btnReset.setOnClickListener {
            score = 0
            currentHold = 0
            hasFallen = false
            reachedTop = false
            updateUI()
        }

        // set initial UI
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", score)
        outState.putInt("hold", currentHold)
        outState.putBoolean("fallen", hasFallen)
        outState.putBoolean("reachedTop", reachedTop)
    }

     fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        // save to preferences
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        prefs.putString("App_Lang", languageCode)
        prefs.apply()

        // restart activity once
        recreate()
    }

     fun loadLocale() {
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = prefs.getString("App_Lang", "en") // default = English
        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

     fun updateUI() {
        // use translated strings
        txtScore.text = getString(R.string.score_label, score)
        txtHold.text = getString(R.string.hold_label, currentHold)
        btnClimb.text = getString(R.string.climb_button)
        btnFall.text = getString(R.string.fall_button)
        btnReset.text = getString(R.string.reset_button)

        val colorRes = when (currentHold) {
            in 1..3 -> android.R.color.holo_blue_light
            in 4..6 -> android.R.color.holo_green_light
            in 7..9 -> android.R.color.holo_red_light
            else -> android.R.color.white
        }

        val colorInt = ContextCompat.getColor(this, colorRes)
        mainLayout.setBackgroundColor(colorInt)

        Log.d(TAG, "updateUI -> hold=$currentHold score=$score fallen=$hasFallen top=$reachedTop colorRes=$colorRes")
    }
}