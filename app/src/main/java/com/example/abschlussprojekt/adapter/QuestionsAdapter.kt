package com.example.abschlussprojekt.adapter
import QuizViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussprojekt.data.model.Question
import com.example.abschlussprojekt.databinding.ItemQuestionBinding

class QuestionAdapter(
    private val dataSet: List<Question>,
    private val viewModel: QuizViewModel
) : RecyclerView.Adapter<QuestionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val question = dataSet[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ItemViewHolder(private val binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            // Bind Frage-Informationen an die Ansichtselemente im Item
            binding.questionText.text = question.questionText

            // Setzt die Antwortmöglichkeiten für die RadioButtons
            binding.option1.text = question.options[0]
            binding.option2.text = question.options[1]
            binding.option3.text = question.options[2]
            binding.option4.text = question.options[3]

            // Setze das Bild der Pflanze
            binding.plantImage.setImageResource(question.plantImage)

            // RadioButton-Click-Listener
            binding.option1.setOnClickListener { viewModel.checkAnswer(question, binding.option1.text.toString()) }
            binding.option2.setOnClickListener { viewModel.checkAnswer(question, binding.option2.text.toString()) }
            binding.option3.setOnClickListener { viewModel.checkAnswer(question, binding.option3.text.toString()) }
            binding.option4.setOnClickListener { viewModel.checkAnswer(question, binding.option4.text.toString()) }
        }
    }
}