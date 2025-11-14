package com.example.week8

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

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

        callThread()
    }

    // Thread 1
    private suspend fun Thread1(): String {
        Log.d("T1", "Start Thread1 on ${Thread.currentThread().name}")
        delay(1000)
        Log.d("T1", "Finish Thread1")
        return "Thread 1"
    }

    // Thread 2
    private suspend fun Thread2(): String {
        Log.d("T2", "Start Thread2 on ${Thread.currentThread().name}")
        delay(1000)
        Log.d("T2", "Finish Thread2")
        return "Thread 2"
    }

    private fun callThread() {
        val txtDisplay = findViewById<TextView>(R.id.txtDisplay)

        CoroutineScope(Dispatchers.IO).launch {
            // --- Sequential Execution ---
            val sequentialTime = measureTimeMillis {
                val result1 = Thread1()
                val result2 = Thread2()
                val result = result1 + result2
                Log.d("T1", "Result: $result")
            }
            Log.d("T1", "execution time: $sequentialTime ms")

            // --- Parallel Execution ---
            val parallelTime = measureTimeMillis {
                val job1 = async { Thread1() }
                val job2 = async { Thread2() }

                val result1 = job1.await()
                val result2 = job2.await()

                Log.d("T1", "Result: $result1 + $result2")
            }
            Log.d("T1", "Execute time: $parallelTime ms")

            withContext(Dispatchers.Main) {
                txtDisplay.text = "Result: ${sequentialTime}ms\nExecute time: ${parallelTime}ms"
            }
        }
    }
}
