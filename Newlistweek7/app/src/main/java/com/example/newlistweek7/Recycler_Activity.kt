package com.example.newlistweek7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Recycler_Activity : AppCompatActivity() {

     lateinit var lsStudents: RecyclerView
    private val strList = arrayOf("Red Mist", "Black Silence", "Vermilion Cross", "Purple Tear")
    private val intList = arrayOf(
        R.drawable.redmist,
        R.drawable.blacksilence,
        R.drawable.vermilioncross,
        R.drawable.purpletear
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview)

        lsStudents = findViewById(R.id.lsStudents)
        lsStudents.layoutManager = LinearLayoutManager(this)
        lsStudents.setHasFixedSize(true)

        val data = ArrayList<DataClass>()

        for(i in 0 until strList.size)
            data.add(DataClass(strList[i].toString()))

        val adapter = RecyclerViewAdapter(data)
        lsStudents.setAdapter(adapter)





    }
}
