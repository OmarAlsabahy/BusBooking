package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Models.UserModel
import com.example.busbooking.R
import com.example.busbooking.ViewModels.SignUpViewModel
import com.example.busbooking.databinding.FragmentSignUpBinding
import com.example.busbooking.gone
import com.example.busbooking.showToast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    val viewModel : SignUpViewModel by viewModels()
    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignUpBinding.bind(view)

        //signIn button listener
        binding.btnSignIn.setOnClickListener {
            findNavController().popBackStack()
        }

        //btnContinue listener
        binding.btnContinue.setOnClickListener {
           validate(
             binding.emailField.text.toString(),
               binding.passwordField.text.toString(),
               binding.confirmField.text.toString(),
               binding.phoneField.text.toString(),
               binding.userNameField.text.toString()
           )
        }

        //observe signUpStatus
        viewModel.signUpStatus.observe(viewLifecycleOwner){status->
            if (status){
                val user = UserModel(auth.currentUser!!.uid , binding.userNameField.text.toString() , binding.emailField.text.toString() , binding.phoneField.text.toString())
                viewModel.addUser(user)
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
            }else{
                requireContext().showToast(requireContext() , "Sign Up Failed")
            }
            binding.progressBar.gone()
        }
    }

    private fun validate(email:String,password:String,confirm:String,phone:String,name:String) {
        if (email.isEmpty() || password.isEmpty() || confirm.isEmpty() || phone.isEmpty() || name.isEmpty()){
            requireContext().showToast(requireContext() , "Please fill all the fields")
        }else{
            if (password.equals(confirm)||email.contains("@")){
                binding.progressBar.visibility = View.VISIBLE
                viewModel.signUp(email , password)
            }else{
                requireContext().showToast(requireContext() , "Please Enter Valid Data")
            }

        }
    }
}