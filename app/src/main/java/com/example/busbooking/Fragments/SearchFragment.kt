package com.example.busbooking.Fragments

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Adapters.SearchTravelAdapter
import com.example.busbooking.ClickListener.BookingClickListener
import com.example.busbooking.R
import com.example.busbooking.ViewModels.SearchViewModel
import com.example.busbooking.databinding.FragmentSearchBinding
import com.example.busbooking.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class SearchFragment : Fragment() , BookingClickListener{
    lateinit var binding: FragmentSearchBinding
    val viewModel : SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchBinding.bind(view)
        val argument = SearchFragmentArgs.fromBundle(requireArguments()).governorate
        binding.userLocationField.setText(argument)

        //observe searched routes
        viewModel.searchedRoutes.observe(viewLifecycleOwner){routes->
            if (routes.isNullOrEmpty()){
                binding.travelsRecycler.visibility = View.GONE
                binding.txtNotAvailable.visibility = View.VISIBLE
            }else{
                val adapter = SearchTravelAdapter(routes,this)
                binding.travelsRecycler.adapter = adapter
                binding.travelsRecycler.visibility = View.VISIBLE
                binding.txtNotAvailable.visibility = View.GONE
            }

        }


        //departure date field listener
        binding.departureDateField.setOnClickListener {
            showDatePickerDialog()
        }


        //btn search listener
        binding.btnSearch.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
       if (!binding.userLocationField.text.isNullOrEmpty()&&binding.destinationField.text.isNullOrEmpty()&&binding.departureDateField.text.isNullOrEmpty()){
           viewModel.getTravelByStart(binding.userLocationField.text.toString())
       }else if (!binding.destinationField.text.isNullOrEmpty()&&binding.userLocationField.text.isNullOrEmpty()&&binding.departureDateField.text.isNullOrEmpty()){
           viewModel.getTravelByEnd(binding.destinationField.text.toString())
       }else if (!binding.departureDateField.text.isNullOrEmpty() && binding.userLocationField.text.isNullOrEmpty()&&binding.destinationField.text.isNullOrEmpty()){
           viewModel.getTravelByDate(binding.departureDateField.text.toString())
       }else if (!binding.userLocationField.text.isNullOrEmpty()&&!binding.destinationField.text.isNullOrEmpty()&&binding.departureDateField.text.isNullOrEmpty()){
           viewModel.getTravelByStartAndEnd(binding.userLocationField.text.toString(),binding.destinationField.text.toString())
       }else if (!binding.userLocationField.text.isNullOrEmpty()&&!binding.departureDateField.text.isNullOrEmpty()&&binding.destinationField.text.isNullOrEmpty()){
           viewModel.getTravelByStartAndDate(binding.userLocationField.text.toString(),binding.departureDateField.text.toString())
       }else if (!binding.destinationField.text.isNullOrEmpty()&&!binding.departureDateField.text.isNullOrEmpty()&&binding.userLocationField.text.isNullOrEmpty()){
           viewModel.getTravelByEndAndDate(binding.destinationField.text.toString(),binding.departureDateField.text.toString())
       }else if (!binding.userLocationField.text.isNullOrEmpty()&&!binding.destinationField.text.isNullOrEmpty()&&!binding.departureDateField.text.isNullOrEmpty()){
           viewModel.getTravelByStartAndEndAndDate(binding.userLocationField.text.toString(),binding.destinationField.text.toString(),binding.departureDateField.text.toString())
       }

       else{
           requireContext().showToast(requireContext(),"Please fill all fields")
       }
    }

    private fun showDatePickerDialog() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val datePicker =DatePickerDialog(requireContext() , {view,year,month,day->
            updateField(year,month,day)
        },year,month,day)
        datePicker.show()
    }

    private fun updateField(year: Int, month: Int, day: Int) {
        val calender = Calendar.getInstance()
        calender.set(year,month,day)
        val formatedDate = SimpleDateFormat("dd-MM-yyyy").format(calender.time)
        binding.departureDateField.setText(formatedDate)
    }

    override fun onClick() {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToBookingFragment())
    }
}