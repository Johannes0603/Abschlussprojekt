package com.example.abschlussprojekt.ui

import QuizViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        // Überprüfe, ob das Quiz bereits abgeschlossen wurde
        if (viewModel.isQuizComplete()) {
            // Wenn das Quiz abgeschlossen ist, zeige den "Ergebnisse anzeigen"-Button
            binding.showResultsButton.visibility = View.VISIBLE
        } else {
            // Wenn das Quiz noch nicht abgeschlossen ist, generiere Fragen und starte das Quiz
            startQuiz()
        }

        // Button-Click-Listener für den Neustart des Spiels
        binding.restartButton.setOnClickListener {
            viewModel.resetQuiz()
            startQuiz()
        }

        // Button-Click-Listener für das Anzeigen der Ergebnisse
        binding.showResultsButton.setOnClickListener {
            if (viewModel.isQuizComplete()) {
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
            } else {
                // Wenn das Quiz noch nicht abgeschlossen ist, zeige eine Nachricht an oder tue nichts
            }
        }
    }

    private fun startQuiz() {
        // Frage-Liste generieren, indem du die plantList an die generateQuestions-Funktion übergibst
        val questions = viewModel.generateQuestions(plantListQuiz)

        // RecyclerView initialisieren
        questionAdapter = QuestionAdapter(questions, viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = questionAdapter
    }
}