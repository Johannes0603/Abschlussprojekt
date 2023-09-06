package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.abschlussprojekt.AuthViewModel
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.databinding.FragmentPWResetBinding


class PWResetFragment: Fragment() {

    private lateinit var binding: FragmentPWResetBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPWResetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btSendReset.setOnClickListener {
            val email: String = binding.tietEmail.text.toString()
            if (email != "") {
                authViewModel.sendPasswordRecovery(email)
            }
        }

    }
}