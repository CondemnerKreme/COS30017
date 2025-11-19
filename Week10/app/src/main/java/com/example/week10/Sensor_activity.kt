package com.example.week10

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Sensor_activity : AppCompatActivity(), SensorEventListener {
    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null
    private lateinit var txtSensorvalues: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_layout)

        txtSensorvalues = findViewById(R.id.txtSensorvalues)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val millibarsOfPressure = event!!.values[0]
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            txtSensorvalues.text = millibarsOfPressure.toString() + " lx"
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }
}
