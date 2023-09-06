package com.example.abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojekt.AuthViewModel
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.databinding.FragmentRegisterBinding


class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        authViewModel.resetRegister()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBack.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btRegister.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            val pass: String = binding.tietPass.text.toString()

            if (email != "" && pass != "") {
                authViewModel.signUp(email, pass)
            }

        }

        authViewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }

        authViewModel.registerFailure.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvFail.visibility = View.VISIBLE
            }
        }
    }

}