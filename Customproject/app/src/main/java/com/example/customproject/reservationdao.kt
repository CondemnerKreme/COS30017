package com.example.customproject.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservation: Reservation)

    @Update
    suspend fun update(reservation: Reservation)

    @Delete
    suspend fun delete(reservation: Reservation)

    @Query("SELECT * FROM reservations ORDER BY date, time")
    fun getAllReservations(): LiveData<List<Reservation>>
}
