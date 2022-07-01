package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.mysevenminworkout.databinding.ActivityBmicalculatorBinding
import com.example.mysevenminworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        initToolbar()

        val historyDao = (application as WorkOutApp).db.HistoryDao()
        getAllCompleteDate( historyDao )

    }

    private fun initToolbar(){
        setSupportActionBar(binding?.toolbarHistory)

        if( supportActionBar != null ){
            supportActionBar?.setTitle(R.string.history_toolbar_title_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getAllCompleteDate( historyDao : HistoryDao ){
        lifecycleScope.launch {
            historyDao.fetchAllHistories().collect { alldatelist ->
                for( i in alldatelist ){
                    Log.e("date:","" + i)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}