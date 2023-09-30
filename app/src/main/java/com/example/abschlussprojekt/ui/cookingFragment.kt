package com.example.abschlussprojekt.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.abschlussprojekt.CookingViewModel
import com.example.abschlussprojekt.adapter.cookingAdapter
import com.example.abschlussprojekt.databinding.FragmentCookingBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abschlussprojekt.R


class cookingFragment : Fragment() {
    private lateinit var binding: FragmentCookingBinding
    private lateinit var adapter: cookingAdapter
    private val viewModel: CookingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Der SnapHelper sorgt dafür, dass die RecyclerView immer auf das aktuelle List Item springt
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.rvCooking)
        binding.viewModel = viewModel
        val recView = binding.rvCooking
        recView.setHasFixedSize(true)
        // Setze das Layout für die RecyclerView
        //recView.layoutManager = LinearLayoutManager(context)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.inputText.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        addObserver()
        // Klick-Listener für den ImageButton hinzufügen
        binding.savedFavoritesCooking.setOnClickListener {
            // Hier zur Ziel-Fragment-Seite (FavoriteFragment) navigieren
            findNavController().navigate(R.id.action_cookingFragment_to_favoritesFragment)
        }



    }

    fun addObserver(){
        viewModel.allRecipes.observe(viewLifecycleOwner,Observer{
            binding.rvCooking.adapter = cookingAdapter(it,viewModel)
        })
    }


}