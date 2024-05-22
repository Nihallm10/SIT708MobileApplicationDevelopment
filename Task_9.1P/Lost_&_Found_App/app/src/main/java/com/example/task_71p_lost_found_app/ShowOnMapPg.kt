package com.example.task_71p_lost_found_app

import android.database.Cursor
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class ShowOnMapPg : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private val itemList = mutableListOf<ItemList>()
    private lateinit var dtbase: DtBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_on_map_pg)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        displayItemsOnMap()
    }

    private fun displayItemsOnMap() {
        dtbase = DtBase(this)
        val cursor = dtbase.getAllItems()
        if (cursor.count == 0) {
            Toast.makeText(this, "NO ITEMS", Toast.LENGTH_SHORT).show()
        } else {
            val boundsBuilder = LatLngBounds.Builder()
            while (cursor.moveToNext()) {
                val item = ItemList(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
                itemList.add(item)
                val locationName = item.location
                addMarkerOnMap(locationName, boundsBuilder)
            }
            cursor.close()
            googleMap.setOnMapLoadedCallback {
                val bounds = boundsBuilder.build()
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
            }
        }
    }

    private fun addMarkerOnMap(locationName: String, boundsBuilder: LatLngBounds.Builder) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocationName(locationName, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses.get(0)
                    val position = address?.let { LatLng(it.latitude, address.longitude) }
                    if (position != null) {
                        boundsBuilder.include(position)
                    }
                    runOnUiThread {
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(position!!)
                                .title(locationName)
                        )
                    }
                }
            }
        } catch (e: IOException) {
            runOnUiThread {
                Toast.makeText(this@ShowOnMapPg, "Error geocoding: $locationName", Toast.LENGTH_SHORT).show()
            }
        }
    }
}