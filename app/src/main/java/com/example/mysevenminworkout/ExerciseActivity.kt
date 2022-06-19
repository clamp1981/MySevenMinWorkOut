package com.example.mysevenminworkout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysevenminworkout.databinding.ActivityExerciseBinding
import com.example.mysevenminworkout.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding : ActivityExerciseBinding? = null
    var restTimer : CountDownTimer? = null
    var workOutTimer : CountDownTimer? = null
    var exerciseArray : ArrayList<ExerciseModel>? = null

    var restTime = 10
    var workOutTime = 30
    var currentExerciseIndex = -1
    var nextExerciseIndex = 0

    var tts : TextToSpeech? = null
    var player : MediaPlayer? = null

    var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts = TextToSpeech( this, this)

        setSupportActionBar(binding?.toolbarExercise)

        if( supportActionBar != null ){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseArray = Constants.getDefultExerciseModel()




        startRestTimer()
        setupExerciseStatusRecyclerView()
    }

    private  fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false )

        exerciseAdapter = ExerciseStatusAdapter( exerciseArray!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
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
        try{
            val soundURI = Uri.parse("android.resource://com.example.mysevenminworkout/" + R.raw.play_action )
            player = MediaPlayer.create( applicationContext, soundURI )
            player?.isLooping = false;
        }catch (e:Exception){
            e.printStackTrace()
        }
        visibilityExerciseControls(false )
        var restTimerCount = 0;
        binding?.restProgressBar?.max = restTime
        binding?.restProgressBar?.progress = restTime;
        if( nextExerciseIndex < exerciseArray!!.size )
            binding?.tvNextExerciseName?.text = "The next exercise is the \r\n" + exerciseArray!![nextExerciseIndex].getName()


        restTimer = object  : CountDownTimer( 10000 , 1000 ){
            override fun onTick(p0: Long) {
                restTimerCount++;
                binding?.restProgressBar?.progress = restTime - restTimerCount;
                binding?.tvRestTimer?.text = (restTime - restTimerCount).toString()
                if( restTime - restTimerCount == 5 )
                    player?.start()
            }

            override fun onFinish() {
                if( currentExerciseIndex < exerciseArray!!.size - 1 ){
                    currentExerciseIndex++
                    nextExerciseIndex++
                    binding?.tvExerciseName?.text = exerciseArray!![currentExerciseIndex].getName()
                    exerciseArray!![currentExerciseIndex].setSelected(true)
                    exerciseAdapter!!.notifyDataSetChanged() //adapter에 알려주기
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


        speakOut("${exerciseArray!![currentExerciseIndex].getName()}")
        workOutTimer = object  : CountDownTimer( 10000 , 1000 ){
            override fun onTick(p0: Long) {
                workOutTimerCount++;
                binding?.workOutProgressBar?.progress = workOutTime - workOutTimerCount;
                binding?.tvWorkOutTimer?.text = (workOutTime - workOutTimerCount).toString()
            }

            override fun onFinish() {
                if( currentExerciseIndex < exerciseArray!!.size - 1 ){
                    exerciseArray!![currentExerciseIndex].setSelected(false)
                    exerciseArray!![currentExerciseIndex].setCompleted(true)

                    exerciseAdapter!!.notifyDataSetChanged() //adapter에 알려주기
                    startRestTimer()
                }else{
                    finish()
                    val intent = Intent( this@ExerciseActivity, FinishActivity::class.java )
                    startActivity(intent)
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
            tts!!.stop()
            tts!!.shutdown()
        }
        if( player!= null){
            player!!.stop()
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