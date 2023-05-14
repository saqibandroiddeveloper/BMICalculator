package com.example.bmicalculator

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    lateinit var weightText: EditText
    lateinit var heightText: EditText
    lateinit var textResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weightText = findViewById<EditText?>(R.id.etWeight)
        heightText = findViewById(R.id.etHeight)
        findViewById<Button>(R.id.btnCalculate).setOnClickListener {


            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            if (inputValidate(weight, height)) {
                val bmi: Float = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                val bmi2Digit = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Digit)
            }
        }
    }

    private fun inputValidate(weight: String, height: String): Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is Empty", Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is Empty", Toast.LENGTH_LONG).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun displayResult(bmi: Float) {
        findViewById<TextView>(R.id.tvIndex).text = bmi.toString()
        findViewById<TextView>(R.id.tvInfo).text = "(Normal Range is 18.50 - 24.99)"
        textResult = findViewById(R.id.tvResult)
        var textDesc: String
        var color: Int
        when {
            bmi < 18.50 -> {
                textDesc = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 -> {
                textDesc = "Normal"
                color = R.color.normal
            }
            bmi in 25.0..29.99 -> {
                textDesc = "Overweight"
                color = R.color.overweight
            }
            else -> {
                textDesc = "Obese"
                color = R.color.obese
            }

        }
        textResult.text = textDesc
        textResult.setTextColor(ContextCompat.getColor(this, color))
        val view: View? = this.currentFocus
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}