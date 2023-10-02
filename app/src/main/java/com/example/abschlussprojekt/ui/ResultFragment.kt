package com.example.abschlussprojekt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.abschlussprojekt.ViewModelPackage.QuizViewModel
import com.example.abschlussprojekt.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hier kannst du die Ergebnisse von QuizViewModel abrufen
        val totalQuestions = viewModel.getTotalQuestions()
        val correctAnswers = viewModel.getCorrectAnswers()

        // Setze die Textfelder mit den Ergebnissen
        binding.tvTotalQuestions.text = "Gesamtzahl der Fragen: $totalQuestions"
        binding.tvCorrectAnswers.text = "Richtige Antworten: $correctAnswers"
    }
}