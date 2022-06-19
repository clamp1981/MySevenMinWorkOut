package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysevenminworkout.databinding.ActivityFinishBinding

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


    }
}