package com.example.week10

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mSensorManager: SensorManager
    private lateinit var lsSensorList: ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_layout)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        val deviceSensors: List<Sensor> = mSensorManager.getSensorList(Sensor.TYPE_ALL)


        val strList: MutableList<String> = ArrayList()
        deviceSensors.forEach {
            Log.v("Sensor", "Sensor: ${it.name}")
            strList.add(it.name)
        }

        lsSensorList = findViewById(R.id.lsSensors)
        val arr = ArrayAdapter(this, R.layout.template_layout, strList)
        lsSensorList.adapter = arr
    }
}
