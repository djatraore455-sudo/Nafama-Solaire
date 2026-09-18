package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.widget.Toast
import com.nafamasolaire.app.ui.theme.*

/**
 * Le choix du moyen de paiement est fonctionnel dans cette démo ; la
 * confirmation simule la transaction (Toast). Une intégration réelle
 * nécessite un contrat marchand avec l'opérateur mobile money (Orange
 * Money API, MTN MoMo API ou Wave API) — c'est une étape de la feuille de
 * route, hors périmètre d'un prototype de fin d'étude.
 */
@Composable
fun PaiementScreen() {
    var operateurChoisi by remember { mutableStateOf("Orange Money") }
    val contexte = LocalContext.current
    val operateurs = listOf(
        Triple("Orange Money", "OM", Color(0xFFFF6600)),
        Triple("MTN Mobile Money", "MTN", Color(0xFFFFC629)),
        Triple("Wave", "W", Color(0xFF1DBFCB))
    )

    Column(Modifier.fillMaxSize().padding(18.dp)) {
        Text("Paiement", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(listOf(VertForet, VertMoyen)), RoundedCornerShape(16.dp))
                .padding(18.dp)
        ) {
            Column {
                Text("Total à payer", color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.bodySmall)
                Text("28 000 FCFA", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
                Text("Tomates fraîches · 80 kg", color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(18.dp))
        Text("Choisir un moyen de paiement", color = TexteGris, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))

        operateurs.forEach { (nom, sigle, couleur) ->
            val selectionne = operateurChoisi == nom
            Card(
                onClick = { operateurChoisi = nom },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = if (selectionne) VertClair else Color.White),
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(36.dp).background(couleur, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) { Text(sigle, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall) }
                    Spacer(Modifier.width(12.dp))
                    Text(nom, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    if (selectionne) Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = VertMoyen)
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { Toast.makeText(contexte, "Paiement simulé avec succès ✓", Toast.LENGTH_LONG).show() },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Confirmer le paiement")
        }
        Text(
            "Transaction sécurisée · commission Nafama Solaire : 3%",
            color = TexteGris,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
