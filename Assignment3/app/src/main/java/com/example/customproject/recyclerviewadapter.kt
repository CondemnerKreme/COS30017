package com.example.customproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.data.Reservation

class ReservationAdapter(
    private var reservations: List<Reservation>,
    private val onItemLongClick: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTable: TextView = itemView.findViewById(R.id.tvTable)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.tvTable.text = reservation.tableNumber
        holder.tvName.text = reservation.customerName
        holder.tvDateTime.text = "${reservation.date} | ${reservation.time}"

        holder.itemView.setOnLongClickListener {
            onItemLongClick(reservation)
            true
        }
    }

    override fun getItemCount(): Int = reservations.size

    fun setData(newReservations: List<Reservation>) {
        reservations = newReservations
        notifyDataSetChanged()
    }
}
