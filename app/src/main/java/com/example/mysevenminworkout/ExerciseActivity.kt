package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.mysevenminworkout.databinding.ActivityExerciseBinding
import com.example.mysevenminworkout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {
    var binding : ActivityExerciseBinding? = null
    var restTimer : CountDownTimer? = null
    var workOutTimer : CountDownTimer? = null
    var restTime = 10
    var workOutTime = 30
    var exerciseArray : ArrayList<ExerciseModel>? = null
    var currentExerciseIndex = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if( supportActionBar != null ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseArray = Constants.getDefultExerciseModel()

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
        binding?.progressBar?.max = restTime
        binding?.progressBar?.progress = restTime;
        restTimer = object  : CountDownTimer( 1000 * 10 , 1000 ){
            override fun onTick(p0: Long) {
                restTimerCount++;
                binding?.progressBar?.progress = restTime - restTimerCount;
                binding?.tvTimer?.text = (restTime - restTimerCount).toString()
            }

            override fun onFinish() {
                resetRestTimer()
                currentExerciseIndex++;
                binding?.tvTitle?.text = exerciseArray!![currentExerciseIndex].getName()
                setWorkOutTimer()
            }
        }.start()
    }


    private fun setWorkOutTimer(){
        var workOutTimerCount = 0;
        binding?.progressBar?.max = workOutTime
        binding?.progressBar?.progress = workOutTime;
        workOutTimer = object  : CountDownTimer( 3000 * 10 , 1000 ){
            override fun onTick(p0: Long) {
                workOutTimerCount++;
                binding?.progressBar?.progress = workOutTime - workOutTimerCount;
                binding?.tvTimer?.text = (workOutTime - workOutTimerCount).toString()
            }

            override fun onFinish() {
                resetWorkOutTimer();
            }
        }.start()
    }

    private fun resetRestTimer(){
        if( restTimer != null )
            restTimer?.cancel()
        restTimer = null
    }

    private fun resetWorkOutTimer(){
        if( workOutTimer != null )
            workOutTimer?.cancel()
        workOutTimer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        resetRestTimer()
        resetWorkOutTimer()
        binding = null
    }
}