package com.example.abschlussprojekt.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.abschlussprojekt.ViewModelPackage.fbPhytoVM
import com.example.abschlussprojekt.adapter.PhytoAdapter
import com.example.abschlussprojekt.data.model.PhytoRecipes
import com.example.abschlussprojekt.databinding.FragmentPhytotherapieBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot



class phytotherapieFragment : Fragment() {
    private val viewModel: fbPhytoVM by activityViewModels()
    private lateinit var binding: FragmentPhytotherapieBinding
    private lateinit var adapter: PhytoAdapter
    private lateinit var db: FirebaseFirestore
    private var PHList: MutableList<PhytoRecipes> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhytotherapieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Der SnapHelper sorgt dafür, dass die RecyclerView immer auf das aktuelle List Item springt
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.rvPhyto)
        val recView = binding.rvPhyto
        recView.setHasFixedSize(true)
        // Hier wird der LinearLayoutManager hinzugefügt
        val layoutManager = LinearLayoutManager(requireContext())
        recView.layoutManager = layoutManager

        // Hier wird der Adapter initialisiert
        adapter = PhytoAdapter(
            PHList,
            viewModel
        ) // Stelle sicher, dass 'PHList' und 'viewModel' korrekt sind
        recView.adapter = adapter
        eventChangeListener()


    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("RezeptePhyt").orderBy("Name", Query.Direction.ASCENDING)
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
                            val phytoRecipe = dc.document.toObject(PhytoRecipes::class.java)
                            PHList.add(phytoRecipe)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            })
    }
}