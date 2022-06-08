package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.mysevenminworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener{

        }

//        val btnStart : FrameLayout = findViewById(R.id.flStart )
//        btnStart.setOnClickListener{
//
//        }
    }
}