package com.example.newlistweek7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val items: MutableList<String>
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "You selected: $item", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = items.size
}
