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

    fun generateQuestions(plantList: List<QuizPlant>): List<Question> {
        val questions = mutableListOf<Question>()
        val usedPlants = mutableSetOf<QuizPlant>()

        while (questions.size < 9) {
            val randomPlant = plantList.random()
            if (randomPlant !in usedPlants) {
                usedPlants.add(randomPlant)
                val answerOptions = mutableListOf<String>()
                val shuffledPlantList = plantList.shuffled()
                repeat(4) {
                    val randomAnswer = shuffledPlantList.random().name
                    answerOptions.add(randomAnswer)
                }
                val correctAnswer = randomPlant.name
                answerOptions.add(correctAnswer)
                answerOptions.shuffle()
                val question = Question("Welche der Pflanzen ist es?", randomPlant.imageResource, answerOptions, correctAnswer)
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