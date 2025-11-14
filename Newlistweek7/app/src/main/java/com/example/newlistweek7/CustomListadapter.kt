package com.example.newlistweek7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomListAdapter(
    context: Context,
    private val dataList: List<DataItem>
) : ArrayAdapter<DataItem>(context, R.layout.template_re_layout, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.template_layout, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imgIcon)
        val textView = view.findViewById<TextView>(R.id.txtName)

        val currentItem = dataList[position]
        imageView.setImageResource(currentItem.imageResId)
        textView.text = currentItem.name

        return view
    }
}
