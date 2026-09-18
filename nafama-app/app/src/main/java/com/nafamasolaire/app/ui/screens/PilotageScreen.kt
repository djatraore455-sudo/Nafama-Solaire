package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nafamasolaire.app.data.SimulateurViewModel
import com.nafamasolaire.app.ui.theme.*

@Composable
fun PilotageScreen(simulateurViewModel: SimulateurViewModel) {
    val etat by simulateurViewModel.etat.collectAsState()

    Column(Modifier.fillMaxSize().padding(18.dp)) {
        Text("Pilotage solaire", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = VertClair)) {
            Column(Modifier.padding(16.dp)) {
                Icon(Icons.Filled.WbCloudy, contentDescription = null, tint = OrSolaire)
                Text("Simulateur actif", color = TexteGris, style = MaterialTheme.typography.bodySmall)
                Text("Ensoleillement ${etat.ensoleillement} — ${etat.heureSimulee}", color = OrSolaire, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            IndicateurTechnique("Batterie", "${etat.batteriePct}%", Icons.Filled.BatteryFull, VertMoyen, Modifier.weight(1f))
            IndicateurTechnique("Débit pompe", "%.1f L/min".format(etat.debitLmin), Icons.Filled.WaterDrop, Color(0xFF3E7FBF), Modifier.weight(1f))
        }

        Spacer(Modifier.height(16.dp))
        Card(shape = RoundedCornerShape(14.dp)) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Column {
                    Text("Pompe d'irrigation", fontWeight = FontWeight.SemiBold)
                    Text(if (etat.pompeActive) "En marche" else "Arrêtée", color = if (etat.pompeActive) VertMoyen else Corail, style = MaterialTheme.typography.bodySmall)
                }
                Switch(checked = etat.pompeActive, onCheckedChange = { simulateurViewModel.basculerPompe() })
            }
        }

        Spacer(Modifier.height(14.dp))
        Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFFFDECEA))) {
            Row(Modifier.padding(14.dp), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Icon(Icons.Filled.WarningAmber, contentDescription = null, tint = Corail)
                Spacer(Modifier.width(8.dp))
                Text("Aucune alerte — batterie et débit dans les normes", color = Corail, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun IndicateurTechnique(label: String, valeur: String, icone: androidx.compose.ui.graphics.vector.ImageVector, couleur: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(14.dp)) {
            Icon(icone, contentDescription = label, tint = couleur)
            Spacer(Modifier.height(6.dp))
            Text(valeur, fontWeight = FontWeight.Bold)
            Text(label, color = TexteGris, style = MaterialTheme.typography.bodySmall)
        }
    }
}
