package com.example.week4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.week4.com.example.week4.BottomFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtUser = findViewById<EditText>(R.id.txtUser)
        val txtPass = findViewById<EditText>(R.id.txtPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = txtUser.text.toString()
            val password = txtPass.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, AdminActivity::class.java)
                intent.putExtra("Username", username)
                intent.putExtra("Password", password)
                startActivity(intent)
            } else {
                txtUser.error = "Enter username"
                txtPass.error = "Enter password"
            }
        }
    }

    // 👇 Move showText INSIDE the MainActivity class
    fun showText(firstName: String, lastName: String) {
        val bottomFragment =
            supportFragmentManager.findFragmentById(R.id.bottom_fragment) as? BottomFragment
        bottomFragment?.updateText("$firstName $lastName")
    }
}
