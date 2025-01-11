package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.R
import com.example.busbooking.ViewModels.LoginViewModel
import com.example.busbooking.databinding.FragmentLoginBinding
import com.example.busbooking.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    val viewModel : LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        //signup button listener
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        //button continue listener
        binding.btnContinue.setOnClickListener {
            viewModel.login(binding.emailField.text.toString() , binding.passwordField.text.toString())
            binding.progressBar.visibility = View.VISIBLE
        }

       //observe loginStatus
       viewModel.loginStatus.observe(viewLifecycleOwner){status->
           if (status){
              findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
           }else{
               requireContext().showToast(requireContext(),"Login Failed")
           }
           binding.progressBar.visibility = View.GONE
       }
    }
}