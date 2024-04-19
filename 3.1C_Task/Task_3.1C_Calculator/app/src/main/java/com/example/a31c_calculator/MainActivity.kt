package com.example.a31c_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextValue1: EditText
    private lateinit var editTextValue2: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonSubtract: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextValue1 = findViewById(R.id.inputvalue1)
        editTextValue2 = findViewById(R.id.inputvalue2)
        buttonAdd = findViewById(R.id.resultadd)
        buttonSubtract = findViewById(R.id.resultsub)
        textViewResult = findViewById(R.id.answer)

        // Set click listeners for buttons
        buttonAdd.setOnClickListener {
            addValues()
        }

        buttonSubtract.setOnClickListener {
            subtractValues()
        }
    }

    private fun addValues() {
        if (editTextValue1.text.toString().isEmpty() || editTextValue2.text.toString().isEmpty()) {
            Toast.makeText(this, "please enter both values", Toast.LENGTH_SHORT).show()
            return
        }
        // Get values from input fields
        val value1 = editTextValue1.text.toString().toInt()
        val value2 = editTextValue2.text.toString().toInt()

        // Perform addition
        val result = value1 + value2

        // Display result
        textViewResult.text = " $result"
    }

    private fun subtractValues() {
        if (editTextValue1.text.toString().isEmpty() || editTextValue2.text.toString().isEmpty()) {
            Toast.makeText(this, "please enter both values", Toast.LENGTH_SHORT).show()
            return
        }
        // Get values from input fields
        val value1 = editTextValue1.text.toString().toInt()
        val value2 = editTextValue2.text.toString().toInt()

        // Perform subtraction
        val result = value1 - value2

        // Display result
        textViewResult.text = " $result"
    }
}
