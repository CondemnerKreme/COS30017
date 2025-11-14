package com.example.customproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RestaurantInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_info)

        val btnReserveNow = findViewById<MaterialButton>(R.id.btnReserveNow)

        btnReserveNow.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            startActivity(intent)
        }
    }
}
