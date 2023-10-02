package com.example.abschlussprojekt.ViewModelPackage


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.abschlussprojekt.data.exampleData.QuizPlant
import com.example.abschlussprojekt.data.model.Question

class QuizViewModel (application: Application) :
    AndroidViewModel(application) {

    private var totalQuestions = 0
    private var correctAnswers = 0
    private val answeredQuestions = mutableListOf<Question>()

    fun generateQuestions(plantList: List<QuizPlant>): List<Question> {
        val questions = mutableListOf<Question>()
        val usedPlants = mutableSetOf<QuizPlant>()

        while (questions.size < 9) { // Solange weniger als 9 Fragen erstellt wurden
            val randomPlant = plantList.random() // Zufällige Pflanze auswählen
            if (randomPlant !in usedPlants) {
                usedPlants.add(randomPlant)
                val answerOptions = mutableListOf<String>()

                // Zufällige Antwortmöglichkeiten generieren (inkl. der richtigen Antwort)
                val shuffledPlantList = plantList.shuffled()
                repeat(4) {
                    val randomAnswer = shuffledPlantList.random().name
                    answerOptions.add(randomAnswer)
                }

                // Die richtige Antwort auf die ausgewählte Pflanze setzen
                val correctAnswer = randomPlant.name
                answerOptions.add(correctAnswer)
                answerOptions.shuffle()

                // Die Frage ist immer dieselbe
                val question = Question("Welche der Pflanzen ist es?", randomPlant.imageResource, answerOptions, correctAnswer)
                questions.add(question)
            }
        }

        totalQuestions = questions.size // Setze die Gesamtanzahl der Fragen
        return questions
    }

    // Funktion zur Überprüfung der Antwort und Aktualisierung der korrekten Antworten
    fun checkAnswer(question: Question, selectedAnswer: String) {
        if (question.correctAnswer == selectedAnswer) {
            correctAnswers++
        }
        answeredQuestions.add(question)
    }

    // Funktionen, um die Ergebnisse abzurufen
    fun getTotalQuestions(): Int {
        return totalQuestions
    }

    fun getCorrectAnswers(): Int {
        return correctAnswers
    }

    // Funktion, um das Quiz zurückzusetzen
    fun resetQuiz() {
        totalQuestions = 0
        correctAnswers = 0
        answeredQuestions.clear()
    }

    fun isQuizComplete(): Boolean {
        // Überprüfe, ob die Anzahl der beantworteten Fragen gleich der Anzahl aller Fragen ist
        return answeredQuestions.size == totalQuestions
    }
}