package com.example.week4.com.example.week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.week4.MainActivity
import com.example.week4.R

class TopFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.top_fragment_layout, container, false)
        val txtFirstName = view.findViewById<EditText>(R.id.firstname)
        val txtLastName = view.findViewById<EditText>(R.id.lastname)
        val btnOk = view.findViewById<Button>(R.id.button_ok)

        btnOk.setOnClickListener {
            val firstName = txtFirstName.text.toString()
            val lastName = txtLastName.text.toString()
            (activity as? MainActivity)?.showText(firstName, lastName)
        }
        return view
    }
}