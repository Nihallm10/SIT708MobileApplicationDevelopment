package com.example.task_71p_lost_found_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class NewAdvertPage : AppCompatActivity() {
    private val itemList = ArrayList<ItemList>()
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var dtbase: DtBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_advert_page)

        saveButton = findViewById(R.id.save)
        nameEditText = findViewById(R.id.name)
        phoneEditText = findViewById(R.id.phone)
        descEditText = findViewById(R.id.desc)
        dateEditText = findViewById(R.id.date)
        locationEditText = findViewById(R.id.location)
        radioGroup = findViewById(R.id.rg1)
        dtbase = DtBase(this)

        dateEditText.inputType = InputType.TYPE_NULL
        dateEditText.isFocusable = false
        dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        saveButton.setOnClickListener {
            if (validateFields()) {
                insertItemIntoDatabase()
            } else {
                Toast.makeText(this@NewAdvertPage, "All Fields are Required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        // Check if any of the fields is empty
        return !nameEditText.text.toString().isEmpty() &&
                !phoneEditText.text.toString().isEmpty() &&
                !descEditText.text.toString().isEmpty() &&
                !dateEditText.text.toString().isEmpty() &&
                !locationEditText.text.toString().isEmpty()
    }

    private fun insertItemIntoDatabase() {
        val selectedId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        val type = selectedRadioButton.text.toString()
        val nameValue = nameEditText.text.toString()
        val phoneValue = phoneEditText.text.toString()
        val descValue = descEditText.text.toString()
        val dateValue = dateEditText.text.toString()
        val locationValue = locationEditText.text.toString()
        val inserted = dtbase.insertItems(nameValue, descValue, locationValue, dateValue, type, phoneValue)
        val message = if (inserted) "Advert Added" else "Advert Failed"
        Toast.makeText(this@NewAdvertPage, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this@NewAdvertPage, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showDatePickerDialog() {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, year, monthOfYear, dayOfMonth ->
                // Using SimpleDateFormat to format the date.
                val dateCal = Calendar.getInstance()
                dateCal.set(Calendar.YEAR, year)
                dateCal.set(Calendar.MONTH, monthOfYear)
                dateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                dateEditText.setText(dateFormat.format(dateCal.time))
            }, year, month, day)
        datePickerDialog.show()
    }
}
