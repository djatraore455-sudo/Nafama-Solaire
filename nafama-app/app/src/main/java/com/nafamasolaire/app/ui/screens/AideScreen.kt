package com.nafamasolaire.app.ui.screens

import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nafamasolaire.app.ui.theme.*
import java.util.Locale

/**
 * Assistant vocal : reconnaissance de la parole (STT) via l'intent système
 * RecognizerIntent, puis réponse parlée (TTS) via TextToSpeech — les deux
 * sont fournis par Android, sans dépendance ni clé API.
 *
 * Limite actuelle : le moteur vocal d'Android couvre bien le français,
 * mais pas le dioula ni le senoufo. Un vrai support de ces langues
 * demanderait un moteur vocal spécialisé — à inscrire en feuille de route.
 */
@Composable
fun AideScreen() {
    val contexte = LocalContext.current
    var derniereQuestion by remember { mutableStateOf<String?>(null) }
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(Unit) {
        val instance = TextToSpeech(contexte) { }
        instance.language = Locale.FRENCH
        tts = instance
        onDispose { instance.shutdown() }
    }

    val lanceurVocal = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { resultat ->
        val texte = resultat.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()
        if (texte != null) {
            derniereQuestion = texte
            val reponse = genererReponse(texte)
            tts?.speak(reponse, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))
        Box(
            Modifier
                .size(110.dp)
                .background(Brush.linearGradient(listOf(OrSolaire, Corail)), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                val intention = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR")
                }
                lanceurVocal.launch(intention)
            }) {
                Icon(Icons.Filled.Mic, contentDescription = "Parler", tint = Color.White, modifier = Modifier.size(42.dp))
            }
        }

        Spacer(Modifier.height(18.dp))
        Text("Parlez-moi, je vous écoute", fontWeight = FontWeight.SemiBold)
        Text("Assistant vocal en français (autres langues en feuille de route)", color = TexteGris, style = MaterialTheme.typography.bodySmall)

        Spacer(Modifier.height(20.dp))
        Card(shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
            Text(
                derniereQuestion?.let { "🔊 « $it »" } ?: "🔊 Appuyez sur le micro pour poser une question",
                modifier = Modifier.padding(14.dp),
                color = TexteGris,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDECEA)),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val intention = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+2250700000000"))
                contexte.startActivity(intention)
            }
        ) {
            Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Headset, contentDescription = null, tint = Corail)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("Parler à un technicien", fontWeight = FontWeight.SemiBold, color = Corail)
                    Text("Problème avec la pompe ou l'app", color = Corail, style = MaterialTheme.typography.bodySmall)
                }
                Icon(Icons.Filled.Phone, contentDescription = "Appeler", tint = Corail)
            }
        }
    }
}

private fun genererReponse(question: String): String {
    val q = question.lowercase()
    return when {
        "gagné" in q || "revenu" in q -> "Cette semaine, vous avez gagné environ deux mille huit cents francs grâce à l'énergie solaire économisée."
        "batterie" in q -> "Votre batterie est actuellement chargée à quatre-vingt-deux pourcent."
        "pompe" in q -> "Votre pompe d'irrigation est actuellement en marche."
        else -> "Je n'ai pas encore la réponse à cette question, mais un technicien peut vous aider."
    }
}
