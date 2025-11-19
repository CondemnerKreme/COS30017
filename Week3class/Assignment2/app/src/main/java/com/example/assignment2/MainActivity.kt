package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentInstrumentIndex = 0
    private var userCreditBalance = 1000
    private var lastBookingInfo: String? = null

    // Make instrumentList mutable
    private var instrumentList = mutableListOf(
        Instrument("Guitar", "Strings", 4.5f, 400),
        Instrument("Piano", "Keyboard", 5.0f, 2000),
        Instrument("Flute", "Wind", 4.0f, 50),
        Instrument("Drumset", "Percussion", 4.3f, 550),
    )

    private val borrowLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                val bookedInstrumentName = result.data?.getStringExtra("booked_instrument")
                val userName = result.data?.getStringExtra("user_name")

                // Find and update the booked instrument
                val bookedInstrument = instrumentList.find { it.name == bookedInstrumentName }
                bookedInstrument?.available = false

                // Deduct the price from credit balance
                bookedInstrument?.let { instrument ->
                    userCreditBalance -= instrument.price
                }

                lastBookingInfo = "Last booking: $userName - $bookedInstrumentName"
                updateBookingDisplay()
                displayCurrentInstrument()

                val successMsg = getString(R.string.booking_confirmed, bookedInstrumentName)
                Toast.makeText(this, successMsg, Toast.LENGTH_LONG).show()
            }
            RESULT_CANCELED -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Apply saved language before setting content view
        LanguageManager.applySavedLanguage(this)

        // Create instruments with localized types
        instrumentList = mutableListOf(
            Instrument("Guitar", getString(R.string.type_strings), 4.5f, 400,
                available = true, imageResId = R.drawable.guitar),
            Instrument("Piano", getString(R.string.type_keyboard), 5.0f, 2000,
                available = true, imageResId = R.drawable.piano),
            Instrument("Flute", getString(R.string.type_wind), 4.0f, 50,
                available = true, imageResId = R.drawable.flute),
            Instrument("Drumset", getString(R.string.type_percussion), 4.3f, 550,
                available = true, imageResId = R.drawable.drumset),

        )

        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)

        // Restore state after configuration change
        if (savedInstanceState != null) {
            currentInstrumentIndex = savedInstanceState.getInt("CURRENT_INDEX", 0)
            userCreditBalance = savedInstanceState.getInt("USER_CREDIT", 1000)
            lastBookingInfo = savedInstanceState.getString("LAST_BOOKING")
        }

        setupLanguageSelection()
        displayCurrentInstrument()
        setupButtonListeners()
        updateBookingDisplay()
    }
    private fun setupLanguageSelection() {
        val languageRadioGroup = findViewById<RadioGroup>(R.id.languageRadioGroup)

        // Set current language selection
        val currentLanguage = LanguageManager.getSavedLanguage(this)
        if (currentLanguage == "vi") {
            findViewById<RadioButton>(R.id.radioVietnamese).isChecked = true
        } else {
            findViewById<RadioButton>(R.id.radioEnglish).isChecked = true
        }


        languageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioEnglish -> {
                    if (LanguageManager.getSavedLanguage(this) != "en") {
                        LanguageManager.updateAppLanguage(this, "en")
                        recreateActivity()
                    }
                }
                R.id.radioVietnamese -> {
                    if (LanguageManager.getSavedLanguage(this) != "vi") {
                        LanguageManager.updateAppLanguage(this, "vi")
                        recreateActivity()
                    }
                }
            }
        }
    }

    private fun recreateActivity() {
        instrumentList = mutableListOf(
            Instrument("Guitar", getString(R.string.type_strings), 4.5f, 400,
                available = true, imageResId = R.drawable.guitar),
            Instrument("Piano", getString(R.string.type_keyboard), 5.0f, 2000,
                available = true, imageResId = R.drawable.piano),
            Instrument("Flute", getString(R.string.type_wind), 4.0f, 50,
                available = true, imageResId = R.drawable.flute),
            Instrument("Drumset", getString(R.string.type_percussion), 4.3f, 550,
                available = true, imageResId = R.drawable.drumset),

        )

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    // Save state during configuration changes
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_INDEX", currentInstrumentIndex)
        outState.putInt("USER_CREDIT", userCreditBalance)
        outState.putString("LAST_BOOKING", lastBookingInfo)
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.btnBorrow).setOnClickListener {
            val currentInstrument = instrumentList[currentInstrumentIndex]

            val intent = Intent(this, Detaildata::class.java).apply {
                putExtra("instrument", currentInstrument)
                putExtra("user_credit", userCreditBalance)
            }
            borrowLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            currentInstrumentIndex = (currentInstrumentIndex + 1) % instrumentList.size
            displayCurrentInstrument()
        }

        findViewById<Button>(R.id.btnPrevious).setOnClickListener {
            currentInstrumentIndex = if (currentInstrumentIndex == 0) {
                instrumentList.size - 1
            } else {
                currentInstrumentIndex - 1
            }
            displayCurrentInstrument()
        }
    }

    private fun displayCurrentInstrument() {
        val instrument = instrumentList[currentInstrumentIndex]

        findViewById<TextView>(R.id.tvName).text = instrument.name
        findViewById<TextView>(R.id.tvType).text = "${getString(R.string.type)}: ${instrument.type}"
        findViewById<TextView>(R.id.tvPrice).text = "${getString(R.string.price)}: ${instrument.price} ${getString(R.string.credits_month)}"
        findViewById<RatingBar>(R.id.ratingBar).rating = instrument.rating

        // Set the instrument image
        findViewById<ImageView>(R.id.ivInstrument).setImageResource(instrument.imageResId)

        // Update availability display
        val availabilityText = if (instrument.available) getString(R.string.available) else getString(R.string.booked)
        val availabilityColor = if (instrument.available) getColor(R.color.green) else getColor(R.color.red)

        findViewById<TextView>(R.id.tvAvailability).text = availabilityText
        findViewById<TextView>(R.id.tvAvailability).setTextColor(availabilityColor)

        // Update borrow button state
        val borrowButton = findViewById<Button>(R.id.btnBorrow)
        borrowButton.isEnabled = instrument.available && userCreditBalance >= instrument.price
        borrowButton.text = if (instrument.available) getString(R.string.borrow) else getString(R.string.booked)
    }

    private fun updateBookingDisplay() {
        // Show remaining credits in the UI if you have a TextView for it
    }
}