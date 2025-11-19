package com.example.week4.com.example.week4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.week4.R

class BottomFragment : Fragment() {

    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_fragment_layout, container, false)
        textView = view.findViewById(R.id.textViewResult)
        return view
    }

    fun updateText(text: String) {
        textView.text = text
    }
}