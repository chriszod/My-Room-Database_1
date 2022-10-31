package com.example.myroomdatabase

import android.app.Application
import com.example.myroomdatabase.database.schedule.AppDatabase

class BusScheduleApplication: Application(){
    val database: AppDatabase by lazy {AppDatabase.getDatabase(this)}
}