package com.example.mysevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysevenminworkout.databinding.ActivityBmicalculatorBinding

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
    }
}