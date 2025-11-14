package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class Detaildata : AppCompatActivity() {

    private var userCreditBalance = 0
    private var currentInstrument: Instrument? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved language before setting content view
        LanguageManager.applySavedLanguage(this)

        setContentView(R.layout.data_detailed)

        // Restore state after configuration change
        if (savedInstanceState != null) {
            // Restore form data if needed
            findViewById<EditText>(R.id.etUserName).setText(
                savedInstanceState.getString("USER_NAME", "")
            )
            findViewById<EditText>(R.id.etUserEmail).setText(
                savedInstanceState.getString("USER_EMAIL", "")
            )
        }

        // Set up back press handling
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setResult(Activity.RESULT_CANCELED)
                Toast.makeText(this@Detaildata, getString(R.string.booking_cancelled), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        // Get data from intent
        currentInstrument = intent.getParcelableExtra<Instrument>("instrument")
        userCreditBalance = intent.getIntExtra("user_credit", 1000)

        if (currentInstrument != null) {
            // Display instrument details with localized strings
            findViewById<TextView>(R.id.detailName).text = currentInstrument!!.name
            findViewById<TextView>(R.id.detailType).text = "${getString(R.string.type)}: ${currentInstrument!!.type}"
            findViewById<RatingBar>(R.id.detailRatingBar).rating = currentInstrument!!.rating
            findViewById<TextView>(R.id.detailPrice).text = "${getString(R.string.price)}: ${currentInstrument!!.price} ${getString(R.string.credits_month)}"

            // Set the instrument image
            findViewById<ImageView>(R.id.detailImage).setImageResource(currentInstrument!!.imageResId)

            val availabilityText = if (currentInstrument!!.available) getString(R.string.available) else getString(R.string.booked)
            findViewById<TextView>(R.id.detailAvailability).text = availabilityText

            // Show credit balance
            findViewById<TextView>(R.id.tvCreditBalance).text = "$userCreditBalance ${getString(R.string.credits_month)}"
        }

        // Set up buttons
        findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            if (validateForm(currentInstrument)) {
                // Save successful - pass data back to MainActivity
                val userName = findViewById<EditText>(R.id.etUserName).text.toString()
                val userEmail = findViewById<EditText>(R.id.etUserEmail).text.toString()

                val resultIntent = Intent().apply {
                    putExtra("booking_result", "SUCCESS")
                    putExtra("booked_instrument", currentInstrument?.name)
                    putExtra("user_name", userName)
                    putExtra("user_email", userEmail)
                }
                setResult(Activity.RESULT_OK, resultIntent)

                // Show success message with localization
                val successMsg = getString(R.string.booking_confirmed, currentInstrument?.name)
                Toast.makeText(this, successMsg, Toast.LENGTH_LONG).show()
                finish()
            }
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            Toast.makeText(this, getString(R.string.booking_cancelled), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Save form data during configuration changes
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("USER_NAME", findViewById<EditText>(R.id.etUserName).text.toString())
        outState.putString("USER_EMAIL", findViewById<EditText>(R.id.etUserEmail).text.toString())
    }

    private fun validateForm(instrument: Instrument?): Boolean {
        val userName = findViewById<EditText>(R.id.etUserName).text.toString()
        val userEmail = findViewById<EditText>(R.id.etUserEmail).text.toString()
        val errorTextView = findViewById<TextView>(R.id.tvError)

        // Reset error
        errorTextView.visibility = TextView.GONE
        errorTextView.text = ""

        // Check required fields with localized error messages
        if (userName.isBlank()) {
            showError(getString(R.string.error_enter_name))
            return false
        }

        if (userEmail.isBlank()) {
            showError(getString(R.string.error_enter_email))
            return false
        }

        // Basic email validation
        if (!userEmail.contains("@") || !userEmail.contains(".")) {
            showError(getString(R.string.error_valid_email))
            return false
        }

        // Check credit balance with localized error message
        instrument?.let {
            if (userCreditBalance < it.price) {
                val errorMsg = getString(R.string.error_insufficient_credit, it.price, userCreditBalance)
                showError(errorMsg)
                return false
            }
        }

        // Check instrument availability with localized error message
        if (instrument?.available != true) {
            showError(getString(R.string.error_not_available))
            return false
        }

        return true
    }

    private fun showError(message: String) {
        val errorTextView = findViewById<TextView>(R.id.tvError)
        errorTextView.text = message
        errorTextView.visibility = TextView.VISIBLE
    }
}