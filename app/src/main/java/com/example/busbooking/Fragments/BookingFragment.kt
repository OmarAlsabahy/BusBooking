package com.example.busbooking.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codebyashish.googledirectionapi.AbstractRouting
import com.codebyashish.googledirectionapi.ErrorHandling
import com.codebyashish.googledirectionapi.RouteDrawing
import com.codebyashish.googledirectionapi.RouteInfoModel
import com.codebyashish.googledirectionapi.RouteListener
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.R
import com.example.busbooking.ViewModels.BookingViewModel
import com.example.busbooking.databinding.FragmentBookingBinding
import com.example.busbooking.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class BookingFragment : Fragment() , OnMapReadyCallback  ,RouteListener{
    lateinit var binding: FragmentBookingBinding
    lateinit var map : GoogleMap
    lateinit var startLatLng:LatLng
    lateinit var endLatLng:LatLng
    lateinit var routeModel:RouteModel
    val viewModel : BookingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentBookingBinding.bind(view)
        routeModel = BookingFragmentArgs.fromBundle(requireArguments()).routeModel
        startLatLng = BookingFragmentArgs.fromBundle(requireArguments()).startLocation.location
        endLatLng = BookingFragmentArgs.fromBundle(requireArguments()).endLocation.location
        viewModel.setData(routeModel)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        viewModel.fare.observe(viewLifecycleOwner){price->
            binding.fare.text = price.toString()
        }


        viewModel.youPay.observe(viewLifecycleOwner){totalPrice->
            binding.youPay.text = totalPrice.toString()

        }

        viewModel.seat.observe(viewLifecycleOwner){seat->
            binding.btnSeat.text = seat.toString()
        }


        viewModel.isBooked.observe(viewLifecycleOwner){isBooked->
            if (isBooked){
                viewModel.sendNotification(requireContext())
                findNavController().popBackStack()
            }else{
                binding.btnSeat.setError("No Available Seats")
            }
        }


        binding.btnAdd.setOnClickListener {
            viewModel.addSeat()
        }

        binding.btnMinus.setOnClickListener {
            if (!binding.btnSeat.text.equals("0")){
                viewModel.minSeat()
            }

        }

        binding.btnBook.setOnClickListener {
            if (binding.btnSeat.text.equals("0")){
                requireContext().showToast(requireContext() , "Please Select Number Of Tickets")
            }else{
                viewModel.book(routeModel,binding.btnSeat.text.toString().toInt())
            }
        }
    }



    override fun onMapReady(p0: GoogleMap) {
        map = p0
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(startLatLng,10f))
        map.addMarker(MarkerOptions().position(startLatLng).title("Start Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        map.addMarker(MarkerOptions().position(endLatLng).title("End Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
        val boundBuilder = LatLngBounds.Builder()
            .include(startLatLng)
            .include(endLatLng)
        val bounds = boundBuilder.build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,100))
        map.addPolyline(PolylineOptions().add(startLatLng , endLatLng).color(Color.BLACK).width(12f))
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
    }

    private fun getRoutePoints(startLatLng: LatLng, endLatLng: LatLng) {
        val routeDrawing = RouteDrawing.Builder()
            .context(requireContext())
            .travelMode(AbstractRouting.TravelMode.DRIVING)
            .withListener(this).alternativeRoutes(true)
            .waypoints(startLatLng , endLatLng)
            .build()
        routeDrawing.execute()
    }

    override fun onRouteFailure(e: ErrorHandling?) {

    }

    override fun onRouteStart() {

    }

    override fun onRouteSuccess(list: ArrayList<RouteInfoModel>?, indexing: Int) {
        requireContext().showToast(requireContext(),"Success")
        if (list != null) {
            var polyLines = arrayListOf<Polyline>()
            for (i in list.indices){
                if (i==indexing){
                    val options = PolylineOptions()
                        .color(Color.BLUE)
                        .width(12f)
                        .addAll(list[indexing].points)
                        .startCap(RoundCap())
                        .endCap(RoundCap())
                    val polyLine = map.addPolyline(options)
                    polyLines.add(polyLine)

                }
            }

        }
    }

    override fun onRouteCancelled() {
        TODO("Not yet implemented")
    }

}