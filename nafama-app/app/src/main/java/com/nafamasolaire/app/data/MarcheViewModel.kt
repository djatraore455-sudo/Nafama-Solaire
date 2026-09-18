package com.nafamasolaire.app.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class MarcheViewModel : ViewModel() {

    private val _annonces = MutableStateFlow(
        listOf(
            Annonce(id = "1", titre = "Tomates fraîches", quantiteKg = 80, prixParKg = 350),
            Annonce(id = "2", titre = "Choux", quantiteKg = 45, prixParKg = 250),
            Annonce(id = "3", titre = "Piments", quantiteKg = 20, prixParKg = 600)
        )
    )
    val annonces: StateFlow<List<Annonce>> = _annonces

    fun ajouterAnnonce(titre: String, quantiteKg: Int, prixParKg: Int, photoUri: String?) {
        val nouvelle = Annonce(
            id = UUID.randomUUID().toString(),
            titre = titre,
            quantiteKg = quantiteKg,
            prixParKg = prixParKg,
            photoUri = photoUri
        )
        _annonces.value = _annonces.value + nouvelle
    }
}
