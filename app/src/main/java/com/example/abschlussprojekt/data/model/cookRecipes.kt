package com.example.abschlussprojekt.data.model

data class cookRecipes (
    var userId: String = "",
    var cookName: String = "",
    var Zubereitung: String = "",
    var img: String = "",
    var like: Boolean = false,
    var dislike: Boolean = false
)
