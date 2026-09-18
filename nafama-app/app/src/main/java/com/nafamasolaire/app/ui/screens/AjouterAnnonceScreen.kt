package com.nafamasolaire.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nafamasolaire.app.data.MarcheViewModel
import com.nafamasolaire.app.ui.theme.*

/**
 * Sélection de photo via le "Photo Picker" natif d'Android (API 33+, et
 * rétrocompatible via Google Play Services sur les versions antérieures) :
 * aucune permission de stockage à demander, aucune dépendance externe.
 */
@Composable
fun AjouterAnnonceScreen(marcheViewModel: MarcheViewModel, navController: NavHostController) {
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var titre by remember { mutableStateOf("") }
    var quantite by remember { mutableStateOf("") }
    var prix by remember { mutableStateOf("") }

    val lanceurPhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> photoUri = uri }

    Column(Modifier.fillMaxSize().padding(18.dp)) {
        Text("Nouvelle annonce", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(2.dp, VertMoyen, RoundedCornerShape(14.dp))
                .background(VertClair, RoundedCornerShape(14.dp))
                .clickable { lanceurPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.AddAPhoto, contentDescription = null, tint = VertMoyen)
                Text(
                    if (photoUri == null) "Ajouter une photo" else "Photo sélectionnée ✓",
                    color = VertMoyen,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = titre, onValueChange = { titre = it }, label = { Text("Produit (ex : Tomates)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(value = quantite, onValueChange = { quantite = it }, label = { Text("Quantité (kg)") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = prix, onValueChange = { prix = it }, label = { Text("Prix / kg (FCFA)") }, modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                marcheViewModel.ajouterAnnonce(
                    titre = titre.ifBlank { "Produit" },
                    quantiteKg = quantite.toIntOrNull() ?: 0,
                    prixParKg = prix.toIntOrNull() ?: 0,
                    photoUri = photoUri?.toString()
                )
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            enabled = titre.isNotBlank() && quantite.isNotBlank() && prix.isNotBlank()
        ) {
            Text("Publier l'annonce")
        }
    }
}
