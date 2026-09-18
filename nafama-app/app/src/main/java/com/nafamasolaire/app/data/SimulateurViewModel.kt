package com.nafamasolaire.app.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Simule les données d'un kit solaire (batterie, ensoleillement, débit) en
 * l'absence de capteurs IoT réels. Les valeurs évoluent légèrement toutes
 * les quelques secondes pour donner une démo "vivante" et réaliste.
 *
 * Pour brancher de vrais capteurs plus tard : remplacer la boucle de
 * simulation ci-dessous par une lecture Bluetooth/Wi-Fi (ex: via un module
 * ESP32 communiquant en MQTT ou HTTP), en conservant le même StateFlow.
 */
class SimulateurViewModel : ViewModel() {

    private val _etat = MutableStateFlow(EtatSolaire())
    val etat: StateFlow<EtatSolaire> = _etat

    init {
        demarrerSimulation()
    }

    private fun demarrerSimulation() {
        viewModelScope.launch {
            while (true) {
                delay(4000)
                val actuel = _etat.value
                val nouvelleBatterie = (actuel.batteriePct + Random.nextInt(-2, 3)).coerceIn(20, 100)
                val nouveauDebit = (actuel.debitLmin + Random.nextDouble(-0.8, 0.8)).coerceIn(8.0, 18.0)
                val nouvellesKwh = (actuel.kwhProduitAujourdhui + Random.nextDouble(0.0, 0.15))
                val nouvellesEconomies = actuel.economiesFcfa + Random.nextInt(0, 40)
                _etat.value = actuel.copy(
                    batteriePct = nouvelleBatterie,
                    debitLmin = nouveauDebit,
                    kwhProduitAujourdhui = nouvellesKwh,
                    economiesFcfa = nouvellesEconomies
                )
            }
        }
    }

    fun basculerPompe() {
        _etat.value = _etat.value.copy(pompeActive = !_etat.value.pompeActive)
    }
}
