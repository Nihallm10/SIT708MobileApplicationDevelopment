package com.example.task_71p_lost_found_app

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NewAdvertPage : AppCompatActivity() {
    private lateinit var uNameEditText: EditText
    private lateinit var uPhoneEditText: EditText
    private lateinit var uDescEditText: EditText
    private lateinit var uDateEditText: EditText
    private lateinit var uLocationEditText: EditText
    private lateinit var uSaveButton: Button
    private lateinit var uRadioGroup: RadioGroup
    private lateinit var uDtBase: DtBase
    private lateinit var uFusedLocationClient: FusedLocationProviderClient
    private var uCurrentLocation: Location? = null

    companion object {
        private const val U_REQUEST_LOCATION_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_advert_page)

        uSaveButton = findViewById(R.id.save)
        uNameEditText = findViewById(R.id.name)
        uPhoneEditText = findViewById(R.id.phone)
        uDescEditText = findViewById(R.id.desc)
        uDateEditText = findViewById(R.id.date)
        uLocationEditText = findViewById(R.id.location)
        uRadioGroup = findViewById(R.id.rg1)
        uDtBase = DtBase(this)
        uFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyB2QfiCvyy4fXeKRkLezrMCpdiWuG8HVYQ")
        }
        uDateEditText.inputType = InputType.TYPE_NULL
        uDateEditText.isFocusable = false
        uDateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        uSaveButton.setOnClickListener {
            if (validateUFields()) {
                insertUItemIntoDatabase()
            } else {
                Toast.makeText(this@NewAdvertPage, "All Fields are Required", Toast.LENGTH_SHORT).show()
            }
        }

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "YOUR_API_KEY")
        }

        val getULocationBtn = findViewById<Button>(R.id.getloc)
        getULocationBtn.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestULocationPermission()
            } else {
                getUCurrentLocation()
            }
        }

        uLocationEditText.isFocusable = false
        uLocationEditText.setOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
            startActivityForResult(intent, 100)
        }
    }

    private fun validateUFields(): Boolean {
        return uNameEditText.text.toString().isNotEmpty() &&
                uPhoneEditText.text.toString().isNotEmpty() &&
                uDescEditText.text.toString().isNotEmpty() &&
                uDateEditText.text.toString().isNotEmpty() &&
                uLocationEditText.text.toString().isNotEmpty()
    }

    private fun insertUItemIntoDatabase() {
        val selectedUId = uRadioGroup.checkedRadioButtonId
        val selectedURadioButton = findViewById<RadioButton>(selectedUId)
        val uType = selectedURadioButton.text.toString()
        val uNameValue = uNameEditText.text.toString()
        val uPhoneValue = uPhoneEditText.text.toString()
        val uDescValue = uDescEditText.text.toString()
        val uDateValue = uDateEditText.text.toString()
        val uLocationValue = uLocationEditText.text.toString()
        val uInserted = uDtBase.insertItems(uNameValue, uDescValue, uLocationValue, uDateValue, uType, uPhoneValue)
        val uMessage = if (uInserted) "Advert Added" else "Advert Failed"
        Toast.makeText(this@NewAdvertPage, uMessage, Toast.LENGTH_SHORT).show()
        val uIntent = Intent(this@NewAdvertPage, MainActivity::class.java)
        startActivity(uIntent)
    }

    private fun showDatePickerDialog() {
        val uCalendar = Calendar.getInstance()
        val uYear = uCalendar.get(Calendar.YEAR)
        val uMonth = uCalendar.get(Calendar.MONTH)
        val uDay = uCalendar.get(Calendar.DAY_OF_MONTH)

        val uDatePickerDialog = DatePickerDialog(this,
            { _, year, monthOfYear, dayOfMonth ->
                val uDateCal = Calendar.getInstance()
                uDateCal.set(Calendar.YEAR, year)
                uDateCal.set(Calendar.MONTH, monthOfYear)
                uDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val uDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                uDateEditText.setText(uDateFormat.format(uDateCal.time))
            }, uYear, uMonth, uDay)
        uDatePickerDialog.show()
    }

    private fun requestULocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), U_REQUEST_LOCATION_PERMISSION)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), U_REQUEST_LOCATION_PERMISSION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUCurrentLocation() {
        uFusedLocationClient.lastLocation.addOnSuccessListener(this, OnSuccessListener { location ->
            if (location != null) {
                uCurrentLocation = location
                convertUCoordinatesToLocation(location.latitude, location.longitude)
            } else {
                Toast.makeText(this@NewAdvertPage, "Location data not available", Toast.LENGTH_SHORT).show()
            }
        }).addOnFailureListener(this, OnFailureListener {
            Toast.makeText(this@NewAdvertPage, "Failed to retrieve location", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val place = Autocomplete.getPlaceFromIntent(data)
            uLocationEditText.setText(place.address)
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data)
            Toast.makeText(this, "Error: ${status.statusMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertUCoordinatesToLocation(latitude: Double, longitude: Double) {
        val uGeocoder = Geocoder(this, Locale.getDefault())
        try {
            val uAddresses = uGeocoder.getFromLocation(latitude, longitude, 1)
            if (uAddresses != null && uAddresses.isNotEmpty()) {
                val uAddress = uAddresses[0]
                val uAddressText = uAddress.getAddressLine(0)
                uLocationEditText.setText(uAddressText)
            } else {
                Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Geocoding of coordinates unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }
}
