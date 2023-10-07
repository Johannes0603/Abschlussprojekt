package com.example.abschlussprojekt.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.abschlussprojekt.databinding.FragmentCookingBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.ViewModelPackage.firebaseCookVM
import com.example.abschlussprojekt.adapter.fbCookingAdapter
import com.example.abschlussprojekt.data.model.cookRecipes
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class cookingFragment : Fragment() {
    private lateinit var binding: FragmentCookingBinding
    private lateinit var adapter: fbCookingAdapter
    private val viewModel: firebaseCookVM by activityViewModels()
    private lateinit var db: FirebaseFirestore
    private var fbCookList: MutableList<cookRecipes> = mutableListOf()

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
        val recView = binding.rvCooking
        recView.setHasFixedSize(true)
        //viewModel.setupProfileRefForCurrentUser(viewModel.currentUser.value)
        // Hier wird der LinearLayoutManager hinzugefügt
        val layoutManager = LinearLayoutManager(requireContext())
        recView.layoutManager = layoutManager
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        adapter = fbCookingAdapter(
            fbCookList,
            viewModel
        )
        recView.adapter = adapter
        //addObserver()
        // Klick-Listener für den ImageButton hinzufügen
        adapter.setOnItemClickListener { selectedRecipe ->
            viewModel.updateRecipeFire(selectedRecipe)
            findNavController().navigate(R.id.action_cookingFragment_to_cookingDetailsFragment)
        }
        binding.btnAddCook.setOnClickListener {
            // Erstellen eines neuen leeren Rezeptobjekts
            val newRecipe = cookRecipes()

            // Setzen des ausgewählten Rezepts im ViewModel auf das neue Rezept
            viewModel.updateRecipeFire(newRecipe)

            // Navigieren zum Details-Fragment (Bearbeitungsmodus)
            findNavController().navigate(R.id.action_cookingFragment_to_cookingDetailsFragment)
        }
        eventChangeListener()
    }

    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("RezepteCook").orderBy("cookName", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("FST Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val cookRecipe = dc.document.toObject(cookRecipes::class.java)
                            if (!fbCookList.contains(cookRecipe))
                                fbCookList.add(cookRecipe)

                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            })
    }
}
