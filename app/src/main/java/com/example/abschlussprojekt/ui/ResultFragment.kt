import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

        viewModel.totalQuestions.observe(viewLifecycleOwner) { totalQuestions ->
            // Setze die Textfelder mit den Ergebnissen
            binding.tvTotalQuestions.text = "Gesamtzahl der Fragen: $totalQuestions"
        }

        viewModel.correctAnswers.observe(viewLifecycleOwner) { correctAnswers ->
            // Setze die Textfelder mit den Ergebnissen
            binding.tvCorrectAnswers.text = "Richtige Antworten: $correctAnswers"
        }
    }
}