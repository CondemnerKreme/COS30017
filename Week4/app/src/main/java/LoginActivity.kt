package com.example.week4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

class LoginActivity : AppCompatActivity() {
        lateinit var viewModel: ViewModel

        // Step 1: Register for activity result
        private val getResult = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
        ) {
                if (it.resultCode == Activity.RESULT_OK) {
                        val value = it.data?.getStringExtra("Test1")
                        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
                }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                // setContentView(R.layout.activity_login)

                // Example: Launch AdminActivity
                val intent = Intent(this, AdminActivity::class.java)
                intent.putExtra("Username", "JohnDoe")
                intent.putExtra("Pass", "1234")
                getResult.launch(intent)
        }

        override fun onStart() {
                super.onStart()
                Log.d("checking", "On start running")
        }

        override fun onPause() {
                super.onPause()
                Log.d("checking", "On Pause")
        }

        override fun onRestart() {
                super.onRestart()
                Log.d("checking", "On Restart")
        }

        override fun onStop() {
                super.onStop()
                Log.d("checking", "On Stop")
        }

        override fun onDestroy() {
                super.onDestroy()
                Log.d("checking", "On Destroy")
        }

        // Example function to start AdminActivity (without expecting result)
        private fun goToAdmin(username: String) {
                val intent = Intent(this, AdminActivity::class.java)
                intent.putExtra("Username", username)
                startActivity(intent)
        }
}
