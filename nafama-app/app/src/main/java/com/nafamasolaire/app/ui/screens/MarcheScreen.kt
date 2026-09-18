package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nafamasolaire.app.data.Annonce
import com.nafamasolaire.app.data.MarcheViewModel
import com.nafamasolaire.app.ui.navigation.Ecran
import com.nafamasolaire.app.ui.theme.*

@Composable
fun MarcheScreen(marcheViewModel: MarcheViewModel, navController: NavHostController) {
    val annonces by marcheViewModel.annonces.collectAsState()

    Column(Modifier.fillMaxSize().padding(18.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Marché", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            IconButton(onClick = { navController.navigate(Ecran.AjouterAnnonce.route) }) {
                Icon(Icons.Filled.Add, contentDescription = "Publier une annonce", tint = VertMoyen)
            }
        }
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(annonces) { annonce ->
                CarteAnnonce(annonce) { navController.navigate(Ecran.Paiement.route) }
            }
        }
    }
}

@Composable
private fun CarteAnnonce(annonce: Annonce, onAcheter: () -> Unit) {
    Card(shape = RoundedCornerShape(16.dp), onClick = onAcheter) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color(0xFFE85D4A).copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Photo, contentDescription = annonce.titre, tint = Color.White.copy(alpha = 0.85f))
            }
            Row(
                Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(annonce.titre, fontWeight = FontWeight.SemiBold)
                    Text("${annonce.quantiteKg} kg · ${annonce.localisation}", color = TexteGris, style = MaterialTheme.typography.bodySmall)
                }
                Text("${annonce.prixParKg}F/kg", fontWeight = FontWeight.Bold, color = VertMoyen)
            }
        }
    }
}
