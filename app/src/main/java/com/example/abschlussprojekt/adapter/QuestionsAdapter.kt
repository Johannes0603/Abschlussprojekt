package com.example.abschlussprojekt.adapter
import QuizViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.data.model.Question
import com.example.abschlussprojekt.databinding.ItemQuestionBinding

class QuestionAdapter(
    private val questions: List<Question>,
    private val viewModel: QuizViewModel
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(private val binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            // Bind Frage-Informationen an die Ansichtselemente im Item
            binding.questionText.text = question.questionText
            binding.option1.text = question.options[0] // Der Pflanzenname für Option 1
            binding.option2.text = question.options[1] // Der Pflanzenname für Option 2
            binding.option3.text = question.options[2] // Der Pflanzenname für Option 3
            binding.option4.text = question.options[3] // Der Pflanzenname für Option 4

            // Setze das Bild der Pflanze
            binding.plantImage.setImageResource(question.plantImage)

            // Weitere Bindungen oder Aktionen hier hinzufügen, falls erforderlich
        }
    }
}