package com.example.customproject

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class reservation_data (
    val table_number: Int,
    val reserved_for: String,
    val phone: String,
    val time: String,
    val date: String,
    val numbers: Int,
) : Parcelable