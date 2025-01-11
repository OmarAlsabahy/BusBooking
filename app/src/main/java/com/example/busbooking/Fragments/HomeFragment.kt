package com.example.busbooking.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Adapters.HomeTravelsAdapter
import com.example.busbooking.R
import com.example.busbooking.ViewModels.HomeViewModel
import com.example.busbooking.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel : HomeViewModel by viewModels()
    @Inject
    lateinit var auth: FirebaseAuth
    val fusedLocation by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    val geoCoder by lazy {
        Geocoder(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get current user
        val userId = auth.currentUser?.uid
        if (userId!=null){
            viewModel.getCurrentUser(userId)
        }

        //check location permission


        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        viewModel.getCurrentUserTravelsByLocation(fusedLocation,geoCoder)
        //observer current user
        viewModel.user.observe(viewLifecycleOwner){
            binding.userName.text = "Hey ${it?.name}"
        }

        //observe governorate Travels
        viewModel.routes.observe(viewLifecycleOwner){routes->
            val adapter = HomeTravelsAdapter(routes)
            binding.locationsRecycler.adapter = adapter
        }

        //observe governorate name
        viewModel.governorate.observe(viewLifecycleOwner){governorate->
            binding.travelLocationText.text = "Travel from $governorate"
        }

        binding.searchBar.setOnClickListener {
            viewModel.governorate.observe(viewLifecycleOwner){governorate->
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment(governorate))
            }

        }

    }
}