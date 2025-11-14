package com.example.customproject.data

import androidx.lifecycle.LiveData

class ReservationRepository(private val dao: ReservationDao) {

    val allReservations: LiveData<List<Reservation>> = dao.getAllReservations()

    suspend fun insert(reservation: Reservation) = dao.insert(reservation)
    suspend fun update(reservation: Reservation) = dao.update(reservation)
    suspend fun delete(reservation: Reservation) = dao.delete(reservation)
}
