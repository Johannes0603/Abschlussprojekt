import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abschlussprojekt.data.exampleData.QuizPlant
import com.example.abschlussprojekt.data.model.Question

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val _totalQuestions = MutableLiveData<Int>(0)
    val totalQuestions: LiveData<Int> = _totalQuestions

    private val _correctAnswers = MutableLiveData<Int>(0)
    val correctAnswers: LiveData<Int> = _correctAnswers

    private val _answeredQuestions = MutableLiveData<MutableList<Question>>(mutableListOf())
    val answeredQuestions: LiveData<MutableList<Question>> = _answeredQuestions

    private val usedPlants = mutableSetOf<QuizPlant>()

    fun generateQuestions(plantList: List<QuizPlant>): List<Question> {
        val questions = mutableListOf<Question>()
        val usedPlants = mutableSetOf<String>() // Hier speichern wir die Namen der Pflanzen, die bereits verwendet wurden

        while (questions.size < 9) {
            val randomPlant = plantList.random()
            if (randomPlant.name !in usedPlants) {
                usedPlants.add(randomPlant.name)

                val answerOptions = mutableListOf<String>()
                answerOptions.add(randomPlant.name) // Die richtige Antwort ist der Name der aktuellen Pflanze

                // Zufällige Antwortmöglichkeiten auswählen, ohne Duplikate
                while (answerOptions.size < 4) {
                    val randomAnswer = plantList.random().name
                    if (randomAnswer != randomPlant.name && randomAnswer !in answerOptions) {
                        answerOptions.add(randomAnswer)
                    }
                }

                answerOptions.shuffle()

                val question = Question("Welche der Pflanzen ist es?", randomPlant.imageResource, answerOptions, randomPlant.name)
                questions.add(question)
            }
        }

        _totalQuestions.value = questions.size
        return questions
    }


    fun checkAnswer(question: Question, selectedAnswer: String) {
        if (question.correctAnswer == selectedAnswer) {
            _correctAnswers.value = (_correctAnswers.value ?: 0) + 1
        }
        val answered = _answeredQuestions.value ?: mutableListOf()
        answered.add(question)
        _answeredQuestions.value = answered
    }

    fun resetQuiz() {
        _totalQuestions.value = 0
        _correctAnswers.value = 0
        _answeredQuestions.value?.clear()
    }

    fun isQuizComplete(): Boolean {
        return _answeredQuestions.value?.size == _totalQuestions.value
    }

    fun updateQuizStats(total: Int, correct: Int, answered: MutableList<Question>) {
        _totalQuestions.value = total
        _correctAnswers.value = correct
        _answeredQuestions.value = answered
    }
}