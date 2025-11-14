package com.example.week8

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnWrite = findViewById<Button>(R.id.btnWrite)
        val btnRead = findViewById<Button>(R.id.btnRead)
        val txtInfo = findViewById<TextView>(R.id.txtInfo)
        val listView = findViewById<ListView>(R.id.listView)


        val externalFile = File(Environment.getExternalStorageDirectory(), "/Documents/test2.txt")

        if (externalFile.exists()) {
            txtInfo.text = externalFile.readText()
        } else {
            Toast.makeText(this, "not exist", Toast.LENGTH_LONG).show()
        }


        btnWrite.setOnClickListener {
            try {
                val file = openFileOutput("test.txt", Context.MODE_APPEND)
                val message = "Only me\n"
                file.write(message.toByteArray())
                file.close()
                Toast.makeText(this, "Message written to internal test.txt", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error writing internal file", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Read from internal file ---
        btnRead.setOnClickListener {
            try {
                val file = openFileInput("test.txt")
                val content = file.bufferedReader(Charsets.UTF_8).readLines()
                file.close()

                txtInfo.text = content.joinToString("\n")

                val arr = ArrayAdapter(this, android.R.layout.simple_list_item_1, content)
                listView.adapter = arr

                Toast.makeText(this, "File read successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error reading internal file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
