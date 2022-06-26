package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysevenminworkout.databinding.ActivityBmicalculatorBinding
import com.example.mysevenminworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        initToolbar()

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
}