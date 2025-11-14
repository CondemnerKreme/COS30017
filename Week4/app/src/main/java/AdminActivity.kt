package com.example.week4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_admin)

        // Receive data from LoginActivity
        val intent = getIntent()
        val user = intent.getStringExtra("Username")
        val pass = intent.getStringExtra("Pass")

        // When exiting, send data back
        val returnIntent = Intent()
        returnIntent.putExtra("Test1", "Hello from AdminActivity")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
