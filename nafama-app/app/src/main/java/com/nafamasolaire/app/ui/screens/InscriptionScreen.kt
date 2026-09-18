package com.nafamasolaire.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nafamasolaire.app.ui.theme.*

@Composable
fun InscriptionScreen(onInscrit: () -> Unit) {
    var telephone by remember { mutableStateOf("") }
    var estProductrice by remember { mutableStateOf(true) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(VertForet, VertMoyen)))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text("☀️", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Text("Nafama Solaire", color = androidx.compose.ui.graphics.Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
        Text("Cultivons ensemble, à l'énergie du soleil", color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.75f))

        Spacer(Modifier.height(30.dp))

        Card(shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp)) {
                Text("Créer mon compte", fontWeight = FontWeight.Bold)
                Text("Juste votre numéro, rien de compliqué", color = TexteGris, style = MaterialTheme.typography.bodySmall)

                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = telephone,
                    onValueChange = { telephone = it },
                    label = { Text("Numéro de téléphone") },
                    leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FilterChip(
                        selected = estProductrice,
                        onClick = { estProductrice = true },
                        label = { Text("Productrice") }
                    )
                    FilterChip(
                        selected = !estProductrice,
                        onClick = { estProductrice = false },
                        label = { Text("Acheteur") }
                    )
                }

                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = onInscrit,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = telephone.length >= 8
                ) {
                    Text("Recevoir mon code par SMS")
                }
            }
        }
    }
}
