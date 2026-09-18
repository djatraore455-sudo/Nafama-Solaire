package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nafamasolaire.app.data.SimulateurViewModel
import com.nafamasolaire.app.ui.navigation.Ecran
import com.nafamasolaire.app.ui.theme.*

@Composable
fun AccueilScreen(simulateurViewModel: SimulateurViewModel, navController: NavHostController) {
    val etat by simulateurViewModel.etat.collectAsState()

    Column(Modifier.fillMaxSize().background(FondClair)) {

        Column(
            Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(listOf(VertForet, VertMoyen)))
                .padding(20.dp)
        ) {
            Text("Bonjour 👋", color = Color.White.copy(alpha = 0.8f))
            Text("Ferme pilote Korhogo", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                IndicateurCarte("Batterie", "${etat.batteriePct}%")
                IndicateurCarte("Soleil", etat.ensoleillement)
                IndicateurCarte("Économies", "${etat.economiesFcfa}F")
            }
        }

        Column(Modifier.padding(18.dp)) {
            Text("Vos modules", style = MaterialTheme.typography.titleMedium, color = TexteGris)
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CarteModule("Pilotage", Icons.Filled.WbSunny, OrSolaire, Modifier.weight(1f)) {
                    navController.navigate(Ecran.Pilotage.route)
                }
                CarteModule("Énergie", Icons.Filled.ShowChart, VertMoyen, Modifier.weight(1f)) {
                    navController.navigate(Ecran.SuiviEnergie.route)
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CarteModule("Marché", Icons.Filled.Storefront, Color(0xFF7A4FC2), Modifier.weight(1f)) {
                    navController.navigate(Ecran.Marche.route)
                }
                CarteModule("Aide", Icons.Filled.SupportAgent, Corail, Modifier.weight(1f)) {
                    navController.navigate(Ecran.Aide.route)
                }
            }
        }
    }
}

@Composable
private fun IndicateurCarte(label: String, valeur: String) {
    Column(
        Modifier
            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Text(valeur, color = Color.White, fontWeight = FontWeight.Bold)
        Text(label, color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun CarteModule(titre: String, icone: androidx.compose.ui.graphics.vector.ImageVector, couleur: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Icon(icone, contentDescription = titre, tint = couleur)
            Spacer(Modifier.height(8.dp))
            Text(titre, fontWeight = FontWeight.SemiBold)
        }
    }
}
