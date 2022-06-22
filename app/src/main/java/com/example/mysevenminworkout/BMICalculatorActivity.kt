package com.example.mysevenminworkout

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mysevenminworkout.databinding.ActivityBmicalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMICalculatorActivity : AppCompatActivity() {

    var binding : ActivityBmicalculatorBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMICalculator)

        if( supportActionBar != null ){
            supportActionBar?.setTitle(R.string.bmi_toolbar_title_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarBMICalculator?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.tvBMILabel?.text = "Your BMI"

        binding?.btnCalculate?.setOnClickListener{
            if( !validateMetricUnity() )
                Toast.makeText(this@BMICalculatorActivity,
                    "Input the Weight and Height values!.", Toast.LENGTH_SHORT).show()

            //자신의 몸무게를 키의 제곱으로 나누는 것
            val weight = binding?.etBMIWeight?.text.toString().toFloat()
            val height = binding?.etBMIHeight?.text.toString().toFloat() / 100

            val bmi = weight / ( height * height )
            displayBMIResult( bmi )
        }

    }

    private fun displayBMIResult(  bmi : Float ){
        val bmiValue  = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN)

        binding?.tvBMI?.text = bmiValue.toString()

        var bmiLabel : String
        var bmiComment : String
        //BMI가 18.5 이하면 저체중 ／ 18.5 ~ 22.9 사이면 정상 ／ 23.0 ~ 24.9 사이면 과체중 ／ 25.0 이상부터는 비만으로 판정.
        if( bmi <= 18.5f  ){
            bmiLabel = "Underweight"
            bmiComment = "You need to take care of yourself!"

        }else if( bmi > 18.5f && bmi <= 22.9f ){
            bmiLabel = "Normal"
            bmiComment = "Congratulation! You are in a good shape!"

        }else if( bmi > 22.9f && bmi <= 24.9f ){
            bmiLabel = "Overweight"
            bmiComment = "Oops! You need to take care of yourself!"

        }else{
            bmiLabel = "Obesity"
            bmiComment = "Oops! You really need to take care of yourself!"
        }

        binding?.tvBMIResult?.text = bmiLabel
        binding?.tvBMIComment?.text = bmiComment
        binding?.rlBMIResult?.visibility = View.VISIBLE

    }

    private fun validateMetricUnity() : Boolean {
        var isValidate = true

        if( binding?.etBMIWeight?.text.toString().isEmpty()
            || binding?.etBMIHeight?.text.toString().isEmpty() )
                isValidate = false

        return  isValidate
    }
}