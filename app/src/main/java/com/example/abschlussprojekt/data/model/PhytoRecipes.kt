package com.example.abschlussprojekt.data.model

import com.google.firebase.firestore.DocumentId

data class PhytoRecipes(
    var Name: String = "",
    var description: String = "",
    var img: String = "",
    var using: String = "",
    @DocumentId val id: String = ""
    )
