package com.nafamasolaire.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nafamasolaire.app.ui.navigation.NafamaApp
import com.nafamasolaire.app.ui.theme.NafamaSolaireTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NafamaSolaireTheme {
                NafamaApp()
            }
        }
    }
}
