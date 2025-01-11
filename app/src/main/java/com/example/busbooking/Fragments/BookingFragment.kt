package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busbooking.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class BookingFragment : Fragment() , OnMapReadyCallback {
    lateinit var map : GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        val startLatLng = LatLng(37.7749, -122.4194) // Example: San Francisco
        val endLatLng = LatLng(34.0522, -118.2437)
        map.addMarker(MarkerOptions().position(startLatLng).title("Start Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(endLatLng))
        val polyOptions = PolylineOptions()
            .add(startLatLng)
            .add(endLatLng)
            .width(10f)
            .color(R.color.mainColor)
        map.addPolyline(polyOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(startLatLng))
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
    }

}