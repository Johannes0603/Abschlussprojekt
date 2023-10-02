package com.example.abschlussprojekt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.ViewModelPackage.QuizViewModel
import com.example.abschlussprojekt.adapter.QuestionAdapter
import com.example.abschlussprojekt.data.exampleData.QuizPlant
import com.example.abschlussprojekt.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var viewModel: QuizViewModel
    private val plantList: List<QuizPlant> = listOf(
        QuizPlant("einstellungen", R.drawable.setting_btn),
        QuizPlant("C", R.drawable.img_c),
        QuizPlant("linse", R.drawable.linsen_ball),
        QuizPlant("rechts", R.drawable.arrow_right),
        QuizPlant("home", R.drawable.home_btn),
        QuizPlant("off", R.drawable.btn_fav_off),
        QuizPlant("scan", R.drawable.btn_scan),
        QuizPlant("cook", R.drawable.cooking_btn),
        QuizPlant("lex", R.drawable.lexicon_btn)
    )

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
            showResults()
        }
        // Immer das Quiz starten, wenn das Fragment geladen wird
        startQuiz()
    }

    private fun startQuiz() {
        // Frage-Liste generieren, indem du die plantList an die generateQuestions-Funktion übergibst
        val questions = viewModel.generateQuestions(plantList)

        // RecyclerView initialisieren
        questionAdapter = QuestionAdapter(questions, viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = questionAdapter
    }

    private fun showResults() {
        // Verstecke den RecyclerView und zeige die Ergebnisse an
        binding.recyclerView.visibility = View.GONE
        binding.resultsLayout.visibility = View.VISIBLE
        binding.showResultsButton.visibility = View.GONE // Verstecke den "Ergebnisse anzeigen"-Button

        // Hier kannst du die Ergebnisse von QuizViewModel abrufen
        val totalQuestions = viewModel.getTotalQuestions()
        val correctAnswers = viewModel.getCorrectAnswers()

        // Setze die Textfelder mit den Ergebnissen
        binding.totalQuestionsTextView.text = "Gesamtzahl der Fragen: $totalQuestions"
        binding.correctAnswersTextView.text = "Richtige Antworten: $correctAnswers"
    }
}