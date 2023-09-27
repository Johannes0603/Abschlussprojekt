package com.example.abschlussprojekt.data.model

data class cookRecipes (
    val name: String,
    val image: Int,
    var like: Boolean = false,
    var dislike: Boolean = false
)