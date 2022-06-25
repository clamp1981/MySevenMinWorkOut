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


        displayMetricBIM()

        binding?.toolbarBMICalculator?.setNavigationOnClickListener {
            onBackPressed()
        }


        binding?.tvBMILabel?.text = "Your BMI"

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId : Int ->
            if( checkedId == R.id.rbMetricUnit ) {
                displayMetricBIM()
            }else{
                displayUSBIM()
            }

        }

        binding?.btnCalculate?.setOnClickListener{
            if( !validateBMIUnity() )
                Toast.makeText(this@BMICalculatorActivity,
                    "Input the Weight and Height values!.", Toast.LENGTH_SHORT).show()

            //자신의 몸무게를 키의 제곱으로 나누는 것
            //val weight = binding?.etBMIWeight?.text.toString().toFloat()
            //val height = binding?.etBMIHeight?.text.toString().toFloat() / 100

            //val bmi = weight / ( height * height )
            displayBMIResult( calculateBMI() )
        }

    }
    private fun calculateBMI() : Float {
        var bmi : Float = 0.0f
        if( binding?.rbMetricUnit?.isChecked == true ){
            val weight = binding?.etBMIWeight?.text.toString().toFloat()
            val height = binding?.etBMIHeight?.text.toString().toFloat() / 100

            bmi = weight / ( height * height )

        }else{
            var feetTocm = binding?.etBMIUSHeightFeet?.text.toString().toFloat() * 30.48f
            var inchTocm = binding?.etBMIUSHeightInch?.text.toString().toFloat() * 2.54f
            val weight = binding?.etBMIUSWeight?.text.toString().toFloat() * 0.45359237f
            val height = (feetTocm +inchTocm)  / 100
            bmi = weight / ( height * height )
        }

        return bmi
    }

    private fun displayMetricBIM(){
        initBMIData()
        binding?.rlMetricUnit?.visibility = View.VISIBLE
        binding?.rlUSUnit?.visibility = View.INVISIBLE

    }

    private fun displayUSBIM(){
        initBMIData()
        binding?.rlMetricUnit?.visibility = View.INVISIBLE
        binding?.rlUSUnit?.visibility = View.VISIBLE

    }

    private fun initBMIData(){
        binding?.etBMIWeight?.setText("")
        binding?.etBMIHeight?.setText("")
        binding?.etBMIUSWeight?.setText("")
        binding?.etBMIUSHeightInch?.setText("")
        binding?.etBMIUSHeightFeet?.setText("")
        binding?.rlBMIResult?.visibility = View.INVISIBLE
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

    private fun validateBMIUnity() : Boolean {
        var isValidate = true

        if( binding?.rbMetricUnit?.isChecked == true ){
            if( binding?.etBMIWeight?.text.toString().isEmpty()
                || binding?.etBMIHeight?.text.toString().isEmpty() )
                isValidate = false
        }

        else{
            if( binding?.etBMIUSWeight?.text.toString().isEmpty()
                || binding?.etBMIUSHeightFeet?.text.toString().isEmpty()
                || binding?.etBMIUSHeightInch?.text.toString().isEmpty())
                isValidate = false
        }



        return  isValidate
    }
}