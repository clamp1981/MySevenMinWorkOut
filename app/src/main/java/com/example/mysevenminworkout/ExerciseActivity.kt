package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mysevenminworkout.databinding.ActivityExerciseBinding
import com.example.mysevenminworkout.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding : ActivityExerciseBinding? = null
    var restTimer : CountDownTimer? = null
    var workOutTimer : CountDownTimer? = null
    var exerciseArray : ArrayList<ExerciseModel>? = null

    var restTime = 5//10
    var workOutTime = 10//30
    var currentExerciseIndex = -1
    var nextExerciseIndex = 0

    var tts : TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts = TextToSpeech( this, this)

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
        visibilityExerciseControls(false )
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
        visibilityExerciseControls(true )
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

    private fun visibilityExerciseControls( isExericse : Boolean ){
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
        if( tts != null ){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if( status == TextToSpeech.SUCCESS ){
            var result = tts?.setLanguage(Locale.US)
            if( result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED ){
                Log.e("TTS", "The Language specified is not supported!!")
            }

        }else{
            Log.e("TTS", "Initialization Failed!!")
        }

    }

    private fun speakOut( speak_text : String ){
        //완전히 초기화 되기 전에 사용하면 not bound to TTS engine 에러남...
        tts?.speak(speak_text, TextToSpeech.QUEUE_FLUSH, null, "" )
    }
}