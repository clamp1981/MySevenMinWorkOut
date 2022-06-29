package com.example.mysevenminworkout

import android.app.Application

class WorkOutApp : Application() {

    //setup database
    val db by lazy{
        HistoryDatabase.getInstance(this)
    }
}