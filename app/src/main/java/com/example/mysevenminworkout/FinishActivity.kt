package com.example.mysevenminworkout

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.mysevenminworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    var binding : ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinish)
        if( supportActionBar != null ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val historyDao = (application as WorkOutApp).db.HistoryDao()
        addDateToDatabase( historyDao)


    }

    private fun addDateToDatabase(historyDao: HistoryDao){

        var calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val sdf = SimpleDateFormat( "dd MM yyyy HH:mm:ss", Locale.getDefault())

        val date = sdf.format(dateTime)
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
        }


    }
}