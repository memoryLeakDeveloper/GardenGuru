package com.example.gardenguru.ui.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor() : ViewModel(){

    private val _calendarTodayMillis = MutableLiveData<Long>()
    private val calendarTodayMillis: LiveData<Long> = _calendarTodayMillis

    fun setCurentCalendar(today: Long){
        _calendarTodayMillis.value = today
    }
}