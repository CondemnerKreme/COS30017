package com.example.myapplicationweek2

import Card
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var txtCard: TextView
    lateinit var btnFlip: Button
    lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCard = findViewById(R.id.txtCard)
        btnFlip = findViewById(R.id.btnFlip)

        card = Card("ACE", "HEARTS")

        txtCard.text = card.getDetails()

        btnFlip.setOnClickListener {
            card.flip()
            txtCard.text = card.getDetails()}
    }
}
