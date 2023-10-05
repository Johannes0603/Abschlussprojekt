package com.example.abschlussprojekt.data.model

data class cookRecipes (
    val CookName: String = "",
    val Zubereitung: String = "",
    val img: String = "",
    var like: Boolean = false,
    var dislike: Boolean = false
)