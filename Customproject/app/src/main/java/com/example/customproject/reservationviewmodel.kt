package com.example.customproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.customproject.data.Reservation
import com.example.customproject.data.ReservationDatabase
import com.example.customproject.data.ReservationRepository
import kotlinx.coroutines.launch

class ReservationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReservationRepository

    val allReservations: LiveData<List<Reservation>>

    init {
        val dao = ReservationDatabase.getDatabase(application).reservationDao()
        repository = ReservationRepository(dao)
        allReservations = repository.allReservations
    }

    fun insert(reservation: Reservation) = viewModelScope.launch {
        repository.insert(reservation)
    }

    fun update(reservation: Reservation) = viewModelScope.launch {
        repository.update(reservation)
    }

    fun delete(reservation: Reservation) = viewModelScope.launch {
        repository.delete(reservation)
    }
}
