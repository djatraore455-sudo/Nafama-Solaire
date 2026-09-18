package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nafamasolaire.app.data.InstallationSolaire
import com.nafamasolaire.app.ui.theme.*

/**
 * Représentation stylisée de la carte des installations, pour la démo.
 * Pour une vraie carte géolocalisée : ajouter la dépendance
 * "com.google.maps.android:maps-compose" + une clé API Google Maps
 * (nécessite un compte Google Cloud avec facturation activée) — c'est une
 * évolution prévue en feuille de route, pas indispensable pour le prototype.
 */
@Composable
fun CarteScreen() {
    val installations = listOf(
        InstallationSolaire("Ferme pilote — Korhogo", 0.5, true, 0.45f, 0.28f),
        InstallationSolaire("Site partenaire 1", 0.3, true, 0.25f, 0.55f),
        InstallationSolaire("Site partenaire 2", 0.4, true, 0.62f, 0.7f)
    )

    Column(Modifier.fillMaxSize()) {
        Text(
            "Installations près de vous",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(18.dp)
        )

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFDCEFE2))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                installations.forEach { site ->
                    drawCircle(
                        color = if (site == installations[0]) VertMoyen else OrSolaire,
                        radius = if (site == installations[0]) 22f else 16f,
                        center = androidx.compose.ui.geometry.Offset(size.width * site.x, size.height * site.y)
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.WbSunny, contentDescription = null, tint = VertMoyen)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(installations[0].nom, fontWeight = FontWeight.SemiBold)
                        Text("${installations[0].surfaceHa} ha · installation active", color = TexteGris, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
