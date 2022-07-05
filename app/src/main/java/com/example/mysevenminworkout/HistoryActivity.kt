package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding?.btnReset?.setOnClickListener{
            deleteAllCompleteData(historyDao)
            getAllCompleteDate( historyDao )

        }

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
                if( alldatelist.isNotEmpty() ){
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataHistory?.visibility = View.GONE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager( this@HistoryActivity )
                    val dates = ArrayList<String>()
                    for( date in alldatelist )
                        dates.add(date.data)

                    val historyAdapter = HistoryAdapter( ArrayList(dates ))
                    binding?.rvHistory?.adapter = historyAdapter

                }else{
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataHistory?.visibility = View.VISIBLE

                }

            }
        }
    }


    private fun deleteAllCompleteData( historyDao : HistoryDao){
        lifecycleScope.launch {
            historyDao.deleteAllHistories()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}