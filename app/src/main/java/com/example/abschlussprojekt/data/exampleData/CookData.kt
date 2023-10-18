package com.example.abschlussprojekt.data.exampleData

import com.example.abschlussprojekt.R
import com.example.abschlussprojekt.data.model.RecipeData

class CookData {
      var recipes: MutableList<RecipeData> = mutableListOf(
        RecipeData(1, "LinsenBalls", "Zubereitung\n" +
                "\n" +
                "\uE192\n" +
                "Arbeitszeit ca. 30 Minuten\n" +
                "\uE192\n" +
                "Koch-/Backzeit ca. 20 Minuten\n" +
                "\uE192\n" +
                "Gesamtzeit ca. 50 Minuten\n" +
                "Brühe mit Lorbeerblatt aufkochen, Linsen einstreuen, aufkochen und abgedeckt circa 10 Minuten garen. Möhren schälen, Zwiebeln abziehen und beides fein reiben (am besten in einem Zerkleinerer).\n" +
                "\n" +
                "Linsen abgießen und gut abtropfen lassen. Möhren, Zwiebeln, Linsen, Haferflocken, Ei, Mehl, Schmand und 2 Esslöffel Schnittlauch vermengen und mit Salz und Pfeffer würzen. Linsenmischung portionsweise (jeweils 2 EL) in erhitztem Öl braten (wie bei Reibeplätzchen).\n" +
                "\n" +
                "Für den Dip Quark und Joghurt mit restlichem Schnittlauch und Petersilie verrühren und mit Salz und Pfeffer würzen. Bratlinge mit dem Dip servieren.\n" +
                "\n" +
                "Dieses Rezept lässt sich schnell und einfach zubereiten.",
            R.drawable.linsen_ball, false, false)
    )

    fun updateRecipe(updatedRecipe: RecipeData) {
        for (index in recipes.indices) {
            if (recipes[index].id == updatedRecipe.id) {
                recipes[index] = updatedRecipe
                break
            }
        }
    }
}
