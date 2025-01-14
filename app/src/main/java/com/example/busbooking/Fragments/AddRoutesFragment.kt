package com.example.busbooking.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AddRoutesViewModel
import com.example.busbooking.databinding.FragmentAddRoutesBinding
import com.example.busbooking.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
@AndroidEntryPoint
class AddRoutesFragment : Fragment() {
    lateinit var binding: FragmentAddRoutesBinding
    val viewModel : AddRoutesViewModel by viewModels()
    var stopsList = arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddRoutesBinding.bind(view)

        //fragment result listener
        setFragmentResultListener("busId",){requestKey,bundle->
            val busId = bundle.getString("busId")
            binding.busId.setText(busId)
        }

        //observe adding status
        viewModel.isAdded.observe(viewLifecycleOwner){
            viewModel.sendNotification(requireContext())
        }

        //observe notification status
        viewModel.isSent.observe(viewLifecycleOwner){status->
            if (status){
                findNavController().popBackStack()
            }
        }
        binding.departureDate.setOnClickListener {

            showDatPickerDialog()
        }

        binding.departureTime.setOnClickListener {
            showTimePickerDialog(binding.departureTime)
        }

        binding.arrivalTime.setOnClickListener {
            showTimePickerDialog(binding.arrivalTime)
        }

        binding.btnAddStop.setOnClickListener {
            validate(binding.stops)
        }

        binding.busId.setOnClickListener {
            findNavController().navigate(AddRoutesFragmentDirections.actionAddRoutesFragmentToAddRoutesBuses())
        }
        binding.btnSave.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val start = binding.start.text.toString().trim()
        val end = binding.end.text.toString().trim()
        val departureDate = binding.departureDate.text.toString().trim()
        val departureTime = binding.departureTime.text.toString().trim()
        val arrivalTime = binding.arrivalTime.text.toString().trim()
        val imageUrl = binding.imageUrl.text.toString().trim()
        val busId = binding.busId.text.toString().trim()
        val price = binding.price.text.toString().trim()
        val intArrivalTime = arrivalTime.split(":")[0].toInt()
        val intDepartureTime = departureTime.split(":")[0].toInt()
        val intTravelTime = intArrivalTime-intDepartureTime
        if (!start.isNullOrEmpty() || !end.isNullOrEmpty() || !departureDate.isNullOrEmpty() || !departureTime.isNullOrEmpty() || !arrivalTime.isNullOrEmpty() || !imageUrl.isNullOrEmpty() || !busId.isNullOrEmpty()){
            viewModel.getRouteBusCapacity(busId) { capacity ->
               val route= RouteModel(start = start , end = end , departureDate = departureDate , departureTime = departureTime,
                    arrivalTime = arrivalTime , imageUrl = imageUrl , busId = busId , availableSeats = capacity , price = price.toFloat(),
                   travelTime = intTravelTime.toString() , stops = stopsList)
                viewModel.addRoute(route)
            }
        }
    }

    private fun validate(stops: EditText) {
        if (stops.text.isNullOrEmpty()){
            requireContext().showToast(requireContext(),"Please enter stop")
        }else{
            stopsList.add(stops.text.toString())
            stops.setText("")
            requireContext().showToast(requireContext(),"Stop added")
        }
    }

    private fun showTimePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(requireContext() , {view,hour,minute->
            updateEditText(hour,minute,editText)
        },hour,minute,true )
        timePicker.show()
    }


    private fun updateEditText(hour: Int, minute: Int, editText: EditText) {
       val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY , hour)
        calendar.set(Calendar.MINUTE , minute)
        val formatedTime = SimpleDateFormat("HH:mm",java.util.Locale.getDefault()).format(calendar.time)
        editText.setText(formatedTime)
    }

    private fun showDatPickerDialog() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_WEEK)
        val datePicker =DatePickerDialog(requireContext() , {view,year,month,day->
            updateDepartureDateFiled(year,month,day)
        },year,month,day)
        datePicker.show()
    }

    private fun updateDepartureDateFiled(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day)
        val formatedDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
        binding.departureDate.setText(formatedDate)
    }
}