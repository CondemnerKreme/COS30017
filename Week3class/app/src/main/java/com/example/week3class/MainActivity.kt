package com.example.week3class

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnOK: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var txtStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind views
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        txtStatus = findViewById(R.id.txtStatus)
        btnOK = findViewById(R.id.btnOK)

        // Hardcoded credentials
        val correctUser = "admin"
        val correctPass = "1234"

        // Handle login when button is clicked
        btnOK.setOnClickListener {
            val user = etUsername.text.toString()
            val pass = etPassword.text.toString()

            if (user == correctUser && pass == correctPass) {
                txtStatus.text = "Login Successful!"
            } else {
                txtStatus.text = "Invalid username or password"
            }
        }
    }
}
