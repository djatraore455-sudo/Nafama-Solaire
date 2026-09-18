package com.nafamasolaire.app.data

data class EtatSolaire(
    val batteriePct: Int = 82,
    val ensoleillement: String = "Fort",
    val debitLmin: Double = 14.0,
    val pompeActive: Boolean = true,
    val kwhProduitAujourdhui: Double = 4.2,
    val economiesFcfa: Int = 2800,
    val heureSimulee: String = "09:40"
)

data class Annonce(
    val id: String,
    val titre: String,
    val quantiteKg: Int,
    val prixParKg: Int,
    val localisation: String = "Korhogo",
    val photoUri: String? = null
)

data class InstallationSolaire(
    val nom: String,
    val surfaceHa: Double,
    val active: Boolean,
    val x: Float,
    val y: Float
)
