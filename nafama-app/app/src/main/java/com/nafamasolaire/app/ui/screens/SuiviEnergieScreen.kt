package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nafamasolaire.app.data.SimulateurViewModel
import com.nafamasolaire.app.ui.theme.*

@Composable
fun SuiviEnergieScreen(simulateurViewModel: SimulateurViewModel) {
    val etat by simulateurViewModel.etat.collectAsState()

    Column(Modifier.fillMaxSize().padding(18.dp)) {
        Text("Suivi énergie", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Production / consommation — aujourd'hui", style = MaterialTheme.typography.bodySmall, color = TexteGris)
                Spacer(Modifier.height(10.dp))
                Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                    val largeur = size.width
                    val hauteur = size.height
                    val production = listOf(0.75f, 0.55f, 0.3f, 0.15f, 0.2f, 0.4f, 0.65f, 0.8f)
                    val consommation = listOf(0.9f, 0.85f, 0.7f, 0.55f, 0.58f, 0.7f, 0.88f, 0.92f)
                    fun tracer(valeurs: List<Float>, couleur: Color) {
                        val pas = largeur / (valeurs.size - 1)
                        for (i in 0 until valeurs.size - 1) {
                            drawLine(
                                color = couleur,
                                start = Offset(i * pas, hauteur * valeurs[i]),
                                end = Offset((i + 1) * pas, hauteur * valeurs[i + 1]),
                                strokeWidth = 5f
                            )
                        }
                    }
                    tracer(production, OrSolaire)
                    tracer(consommation, VertMoyen)
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) {
                Column(Modifier.padding(14.dp)) {
                    Text("%.1f kWh".format(etat.kwhProduitAujourdhui), fontWeight = FontWeight.Bold, color = OrSolaire)
                    Text("Produits aujourd'hui", color = TexteGris, style = MaterialTheme.typography.bodySmall)
                }
            }
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) {
                Column(Modifier.padding(14.dp)) {
                    Text("${etat.economiesFcfa} FCFA", fontWeight = FontWeight.Bold, color = VertMoyen)
                    Text("Économisés vs diesel", color = TexteGris, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = VertClair)) {
            Row(Modifier.padding(14.dp)) {
                Icon(Icons.Filled.Lightbulb, contentDescription = null, tint = VertMoyen)
                Spacer(Modifier.width(8.dp))
                Text("Meilleure fenêtre d'irrigation aujourd'hui : 11h–14h", color = VertMoyen, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
