package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.mysevenminworkout.databinding.ActivityExerciseBinding
import com.example.mysevenminworkout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {
    var binding : ActivityExerciseBinding? = null
    var restTimer : CountDownTimer? = null
    var restTime = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if( supportActionBar != null ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        startRestTimer()
    }

    private fun startRestTimer() {
        resetRestTimer()
        setRestTimer()
    }

    private fun setRestTimer(){
        var restTimerCount = 0;
        binding?.progressBar?.progress = restTime;
        restTimer = object  : CountDownTimer( 1000 * 10 , 1000 ){
            override fun onTick(p0: Long) {
                restTimerCount++;
                binding?.progressBar?.progress = restTime - restTimerCount;
                binding?.tvTimer?.text = (restTime - restTimerCount).toString()
            }

            override fun onFinish() {
                resetRestTimer()
            }
        }.start()
    }

    private fun resetRestTimer(){
        if( restTimer != null )
            restTimer?.cancel()
        restTimer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        resetRestTimer()
        binding = null
    }
}