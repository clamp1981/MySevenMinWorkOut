package com.example.mysevenminworkout

import android.content.Intent
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
            val intent = Intent( this, ExerciseActivity::class.java )
            startActivity(intent)

        }

        binding?.flBMI?.setOnClickListener{
            val intent = Intent( this, BMICalculatorActivity::class.java )
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

//        val btnStart : FrameLayout = findViewById(R.id.flStart )
//        btnStart.setOnClickListener{
//
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}