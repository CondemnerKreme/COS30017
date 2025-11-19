package com.example.customproject

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.customproject.data.Reservation
import com.example.customproject.ui.ReservationAdapter
import com.example.customproject.viewmodel.ReservationViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.util.Calendar

class ReservationActivity : AppCompatActivity() {

    private val reservationViewModel: ReservationViewModel by viewModels()
    private lateinit var adapter: ReservationAdapter

    private var selectedDate = ""
    private var selectedTime = ""
    private var selectedTable = ""
    private var numPeople = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etPhone = findViewById<TextInputEditText>(R.id.etPhone)
        val btnSubmit = findViewById<MaterialButton>(R.id.btnSubmit)
        val btnCancel = findViewById<MaterialButton>(R.id.btnCancel)
        val btnDate = findViewById<MaterialButton>(R.id.btndate)

        val recyclerView = findViewById<RecyclerView>(R.id.reservationRecyclerView)
        adapter = ReservationAdapter(listOf()) { reservation ->
            // Delete reservation from local database
            reservationViewModel.delete(reservation)
            Toast.makeText(this, "Reservation deleted", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe LiveData from Room
        reservationViewModel.allReservations.observe(this) { list ->
            adapter.setData(list)
        }

        // --- Date Picker ---
        btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    btnDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // --- Time Buttons ---
        findViewById<MaterialButton>(R.id.btn6pm).setOnClickListener { selectedTime = "6:00 PM" }
        findViewById<MaterialButton>(R.id.btn7pm).setOnClickListener { selectedTime = "7:00 PM" }
        findViewById<MaterialButton>(R.id.btn8pm).setOnClickListener { selectedTime = "8:00 PM" }

        // --- Table Buttons ---
        val tableButtonIds = listOf(
            R.id.table1, R.id.table2, R.id.table3,
            R.id.table4, R.id.table5, R.id.table6
        )
        for (id in tableButtonIds) {
            findViewById<MaterialButton>(id).setOnClickListener { btn ->
                selectedTable = (btn as MaterialButton).text.toString()
            }
        }

        // --- Submit Button ---
        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()

            if (name.isEmpty() || phone.isEmpty() || selectedDate.isEmpty() ||
                selectedTime.isEmpty() || selectedTable.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val reservation = Reservation(
                tableNumber = selectedTable,
                customerName = name,
                phone = phone,
                date = selectedDate,
                time = selectedTime,
                numPeople = numPeople
            )

            // Save to local Room database
            reservationViewModel.insert(reservation)

            // Send to Google Sheet
            sendReservationToSheet(reservation)

            Toast.makeText(this, "Reservation saved", Toast.LENGTH_SHORT).show()

            // Clear input fields
            etName.text?.clear()
            etPhone.text?.clear()
            selectedDate = ""
            selectedTime = ""
            selectedTable = ""
            btnDate.text = "Select Date"
        }

        // --- Cancel Button ---
        btnCancel.setOnClickListener { finish() }
    }

    private fun sendReservationToSheet(reservation: Reservation) {
        val url = "https://script.google.com/macros/s/AKfycbyH0aCHRiv2Y9xJisyiHDZRSs3H9-3ibXNekLcAIqckPBxD5-4T-2DikgM8yx0KcMUY/exec"
        val params = "?name=${reservation.customerName}" +
                "&phone=${reservation.phone}" +
                "&date=${reservation.date}" +
                "&time=${reservation.time}" +
                "&table=${reservation.tableNumber}" +
                "&numPeople=${reservation.numPeople}"
        val fullUrl = url + params
        val request = object : com.android.volley.toolbox.StringRequest(
            Method.GET, fullUrl,
            { response ->
                Toast.makeText(this, "Sent to spreadsheet!", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                return mapOf("Content-Type" to "application/json")
            }
        }

        Volley.newRequestQueue(this).add(request)
    }
}
