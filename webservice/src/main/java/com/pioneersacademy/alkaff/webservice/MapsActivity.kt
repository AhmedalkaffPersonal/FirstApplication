package com.pioneersacademy.alkaff.webservice

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Amman and move the camera

        val bitmap:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.car)
        val smallBitmap:Bitmap = Bitmap.createScaledBitmap(bitmap,100,100,false)
        val sec = BitmapDescriptorFactory.fromBitmap(smallBitmap)
        val amman = LatLng(31.9539494, 35.910635)
        val amman1 = LatLng(31.9439400, 35.910600)
        mMap.addMarker(MarkerOptions()
            .position(amman)
            .title("Amman")
            .snippet("Welcome to Amman")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            .alpha(0.7f)
            .zIndex(3.0F)
            .draggable(true)
        )

        mMap.addMarker(MarkerOptions()
            .position(amman1)
            .title("Amman1")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            .alpha(0.5f)
            .zIndex(2.0F)
            .draggable(true)

        )

        val ployopt = PolylineOptions()
            .add(LatLng(31.9539494, 35.910635))
            .add(LatLng(31.9539494, 35.911600))
            .add(LatLng(31.9559494, 35.915635))
            .add(LatLng(31.9539494, 35.910635))

        val polyline = mMap.addPolyline(ployopt)

        val lat = LatLng(31.35, 35.90)
        val rect = PolygonOptions()
            .add(LatLng(31.35, 35.90))
            .add(LatLng(31.45, 35.90))
            .add(LatLng(31.45, 36.1))
            .add(LatLng(31.35, 36.1))
            .add(LatLng(31.35, 35.90))
            .strokeColor(Color.RED)
//            .fillColor(Color.BLUE)

        mMap.addPolygon(rect)

        val circle = CircleOptions()
            .center(lat)
            .radius(1000.0)
            .clickable(true)

        mMap.addCircle(circle)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat,10f))

        mMap.setOnCircleClickListener {
            it.fillColor = Color.RED
        }
    }
}