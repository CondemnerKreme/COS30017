package com.example.week2app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnOK: Button;
    lateinit var btnCancel: Button;
    lateinit var txtName: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnOK = findViewById(R.id.btnOK)
        btnCancel = findViewById(R.id.btnCancel)
        txtName = findViewById(R.id.txtName)

        btnOK.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var text = txtName.text
        when (v?.id) {
            R.id.btnOK -> {
                txtName.setText ( "Hello $text" )
            }

            R.id.btnCancel -> {
                txtName.setText ( "Cancel" )
            }
        }
    }
}
