package com.example.unitapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var inputEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var convertedUnitTextView: TextView
    private lateinit var convertButton: Button
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner

    private val categories = arrayOf("Length", "Temperature", "Weight")
    private val unitsMap = mapOf(
        "Length" to arrayOf("Inch", "Yard", "Foot", "Mile", "Meter", "Centimeter"),
        "Temperature" to arrayOf("Celsius", "Fahrenheit", "Kelvin"),
        "Weight" to arrayOf("Pound", "Ounce", "Ton")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.input_value)
        resultTextView = findViewById(R.id.result_value)
        convertedUnitTextView = findViewById(R.id.converted_unit_textview)
        convertButton = findViewById(R.id.convert_button)
        fromUnitSpinner = findViewById(R.id.from_type)
        toUnitSpinner = findViewById(R.id.to_type)

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        findViewById<Spinner>(R.id.unit_type).apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    switchUnits()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        convertButton.setOnClickListener {
            onConvertButtonClicked()
        }
    }

    private fun switchUnits() {
        val category = findViewById<Spinner>(R.id.unit_type).selectedItem.toString()
        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitsMap[category] ?: arrayOf())
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromUnitSpinner.adapter = unitAdapter
        toUnitSpinner.adapter = unitAdapter
    }

    private fun convertValue(fromUnit: String, toUnit: String, inputValue: Double): Pair<Double, String> {
        return when {
            // Length Conversions
            fromUnit == "Inch" && toUnit == "Yard" -> Pair(inputValue / 36, "Yard")
            fromUnit == "Inch" && toUnit == "Foot" -> Pair(inputValue / 12, "Foot")
            fromUnit == "Inch" && toUnit == "Mile" -> Pair(inputValue / 63360, "Mile")
            fromUnit == "Yard" && toUnit == "Inch" -> Pair(inputValue * 36, "Inch")
            fromUnit == "Yard" && toUnit == "Foot" -> Pair(inputValue * 3, "Foot")
            fromUnit == "Yard" && toUnit == "Mile" -> Pair(inputValue / 1760, "Mile")
            fromUnit == "Foot" && toUnit == "Inch" -> Pair(inputValue * 12, "Inch")
            fromUnit == "Foot" && toUnit == "Yard" -> Pair(inputValue / 3, "Yard")
            fromUnit == "Foot" && toUnit == "Mile" -> Pair(inputValue / 5280, "Mile")
            fromUnit == "Mile" && toUnit == "Inch" -> Pair(inputValue * 63360, "Inch")
            fromUnit == "Mile" && toUnit == "Yard" -> Pair(inputValue * 1760, "Yard")
            fromUnit == "Mile" && toUnit == "Foot" -> Pair(inputValue * 5280, "Foot")
            fromUnit == "Meter" && toUnit == "Centimeter" -> Pair(inputValue * 100, "Centimeter")
            fromUnit == "Meter" && toUnit == "Inch" -> Pair(inputValue * 39.3701, "Inch")
            fromUnit == "Centimeter" && toUnit == "Meter" -> Pair(inputValue / 100, "Meter")
            fromUnit == "Centimeter" && toUnit == "Inch" -> Pair(inputValue * 0.393701, "Inch")
            fromUnit == "Inch" && toUnit == "Centimeter" -> Pair(inputValue * 2.54, "Centimeter")
            fromUnit == "Inch" && toUnit == "Meter" -> Pair(inputValue * 0.0254, "Meter")

            // Temperature Conversions
            fromUnit == "Celsius" && toUnit == "Fahrenheit" -> Pair(inputValue * 1.8 + 32, "Fahrenheit")
            fromUnit == "Fahrenheit" && toUnit == "Celsius" -> Pair((inputValue - 32) / 1.8, "Celsius")
            fromUnit == "Celsius" && toUnit == "Kelvin" -> Pair(inputValue + 273.15, "Kelvin")
            fromUnit == "Kelvin" && toUnit == "Celsius" -> Pair(inputValue - 273.15, "Celsius")
            fromUnit == "Fahrenheit" && toUnit == "Kelvin" -> Pair((inputValue - 32) * 5 / 9 + 273.15, "Kelvin")
            fromUnit == "Kelvin" && toUnit == "Fahrenheit" -> Pair(inputValue * 9 / 5 - 459.67, "Fahrenheit")

            // Weight Conversions
            fromUnit == "Pound" && toUnit == "Ounce" -> Pair(inputValue * 16, "Ounce")
            fromUnit == "Pound" && toUnit == "Ton" -> Pair(inputValue / 2000, "Ton")
            fromUnit == "Ounce" && toUnit == "Pound" -> Pair(inputValue / 16, "Pound")
            fromUnit == "Ounce" && toUnit == "Ton" -> Pair(inputValue / 32000, "Ton")
            fromUnit == "Ton" && toUnit == "Pound" -> Pair(inputValue * 2000, "Pound")
            fromUnit == "Ton" && toUnit == "Ounce" -> Pair(inputValue * 32000, "Ounce")

            else -> {
                Toast.makeText(this, "Invalid conversion.", Toast.LENGTH_SHORT).show()
                Pair(0.0, "")
            }
        }
    }

    private fun onConvertButtonClicked() {
        val inputStr = inputEditText.text.toString().trim()
        if (inputStr.isNotEmpty()) {
            if (!inputStr.isNumeric()) {
                Toast.makeText(this, "Please enter a valid numeric value.", Toast.LENGTH_SHORT).show()
            } else {
                val inputValue = inputStr.toDouble()
                val fromUnit = fromUnitSpinner.selectedItem.toString()
                val toUnit = toUnitSpinner.selectedItem.toString()

                if (fromUnit == toUnit) {
                    Toast.makeText(this, "Source and destination units are the same.", Toast.LENGTH_SHORT).show()
                } else {
                    val (result, convertedUnit) = convertValue(fromUnit, toUnit, inputValue)
                    resultTextView.text = result.toString()
                    convertedUnitTextView.text = "$convertedUnit"
                }
            }
        } else {
            Toast.makeText(this, "Enter a value.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun String.isNumeric(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
