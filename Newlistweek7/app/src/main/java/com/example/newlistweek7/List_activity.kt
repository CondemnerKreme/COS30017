package com.example.newlistweek7

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    private lateinit var lsStudents: ListView
    private val strList = arrayOf("Red Mist", "Black Silence", "Vermilion Cross", "Purple Tear")
    private val intList = arrayOf(
        R.drawable.redmist,
        R.drawable.blacksilence,
        R.drawable.vermilioncross,
        R.drawable.purpletear
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lsStudents = findViewById(R.id.lsStudents)
        val adapter = StudentAdapter(this, strList, intList)
        lsStudents.adapter = adapter

        lsStudents.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = strList[position]
            Toast.makeText(this, "You selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }
}
