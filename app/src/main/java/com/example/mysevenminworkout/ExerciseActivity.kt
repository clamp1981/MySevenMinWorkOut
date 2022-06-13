package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.mysevenminworkout.databinding.ActivityExerciseBinding
import com.example.mysevenminworkout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {
    var binding : ActivityExerciseBinding? = null
    var restTimer : CountDownTimer? = null
    var workOutTimer : CountDownTimer? = null
    var exerciseArray : ArrayList<ExerciseModel>? = null

    var restTime = 5//10
    var workOutTime = 10//30
    var currentExerciseIndex = -1
    var nextExerciseIndex = 0

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

    private fun startWorkOutTimer() {
        resetWorkOutTimer()
        setWorkOutTimer()
    }

    private fun setRestTimer(){
        visiblityExerciseControls(false )
        var restTimerCount = 0;
        binding?.restProgressBar?.max = restTime
        binding?.restProgressBar?.progress = restTime;
        if( nextExerciseIndex < exerciseArray!!.size )
            binding?.tvNextExerciseName?.text = "The next exercise is the \r\n" + exerciseArray!![nextExerciseIndex].getName()

        restTimer = object  : CountDownTimer( 5000 , 1000 ){
            override fun onTick(p0: Long) {
                restTimerCount++;
                binding?.restProgressBar?.progress = restTime - restTimerCount;
                binding?.tvRestTimer?.text = (restTime - restTimerCount).toString()
            }

            override fun onFinish() {
                if( currentExerciseIndex < exerciseArray!!.size - 1 ){
                    currentExerciseIndex++
                    nextExerciseIndex++
                    binding?.tvExerciseName?.text = exerciseArray!![currentExerciseIndex].getName()
                    startWorkOutTimer()
                }

            }
        }.start()
    }


    private fun setWorkOutTimer(){
        visiblityExerciseControls(true )
        var workOutTimerCount = 0;
        binding?.workOutProgressBar?.max = workOutTime
        binding?.workOutProgressBar?.progress = workOutTime;
        workOutTimer = object  : CountDownTimer( 10000 , 1000 ){
            override fun onTick(p0: Long) {
                workOutTimerCount++;
                binding?.workOutProgressBar?.progress = workOutTime - workOutTimerCount;
                binding?.tvWorkOutTimer?.text = (workOutTime - workOutTimerCount).toString()
            }

            override fun onFinish() {
                if( currentExerciseIndex < exerciseArray!!.size - 1 )
                    startRestTimer()
                else{
                    Toast.makeText(this@ExerciseActivity, "운동 끝", Toast.LENGTH_LONG).show()
                }

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

    private fun visiblityExerciseControls( isExericse : Boolean ){
        if( isExericse ){
            binding?.llRest?.visibility = View.INVISIBLE
            binding?.llWorkOut?.visibility = View.VISIBLE
        }else{
            binding?.llRest?.visibility = View.VISIBLE
            binding?.llWorkOut?.visibility = View.INVISIBLE

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        resetRestTimer()
        resetWorkOutTimer()
        binding = null
    }
}