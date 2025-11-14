package com.example.week4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import android.util.Log

class SampleViewModel : ViewModel() {

    // Backing property
    private val _count = MutableLiveData<Int>(0)
    val badgeCount: LiveData<Int> get() = _count

    private var number = 0

    var username: String = "Sancho"

    fun incrementBadgeCount() {
        _count.postValue(++number)
    }

    fun updateUsername(newName: String) {
        username = newName
    }

    // Proper override
    override fun onCleared() {
        super.onCleared()
        Log.d("SampleViewModel", "ViewModel cleared")
    }
}
