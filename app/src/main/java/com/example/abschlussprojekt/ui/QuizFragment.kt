package com.example.abschlussprojekt.ui

import QuizViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.adapter.QuestionAdapter

import com.example.abschlussprojekt.data.exampleData.plantListQuiz

import com.example.abschlussprojekt.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var questionAdapter: QuestionAdapter
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startQuiz() // Starten Sie das Quiz, unabhängig davon, ob es bereits abgeschlossen ist

        // Button-Click-Listener für den Neustart des Spiels
        binding.restartButton.setOnClickListener {
            viewModel.resetQuiz()
            startQuiz()
        }

        // Button-Click-Listener für das Anzeigen der Ergebnisse
        binding.showResultsButton.setOnClickListener {
            val totalQuestions = viewModel.totalQuestions.value ?: 0
            val correctAnswers = viewModel.correctAnswers.value ?: 0
            val answeredQuestions = viewModel.answeredQuestions.value ?: mutableListOf()
            viewModel.updateQuizStats(totalQuestions, correctAnswers, answeredQuestions)
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
        }
    }


    private fun startQuiz() {
        // Frage-Liste generieren, indem die plantList an die generateQuestions-Funktion übergeben wird
        val questions = viewModel.generateQuestions(plantListQuiz)

        // RecyclerView initialisieren
        questionAdapter = QuestionAdapter(questions, viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = questionAdapter
    }
}