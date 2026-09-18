package com.nafamasolaire.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nafamasolaire.app.data.MarcheViewModel
import com.nafamasolaire.app.data.SimulateurViewModel
import com.nafamasolaire.app.ui.screens.*

sealed class Ecran(val route: String, val label: String) {
    object Inscription : Ecran("inscription", "Inscription")
    object Accueil : Ecran("accueil", "Accueil")
    object Pilotage : Ecran("pilotage", "Pilotage")
    object SuiviEnergie : Ecran("suivi_energie", "Énergie")
    object Marche : Ecran("marche", "Marché")
    object AjouterAnnonce : Ecran("ajouter_annonce", "Nouvelle annonce")
    object Paiement : Ecran("paiement", "Paiement")
    object Carte : Ecran("carte", "Carte")
    object Aide : Ecran("aide", "Aide")
}

private val ongletsBarreBasse = listOf(Ecran.Accueil, Ecran.Carte, Ecran.Marche, Ecran.Aide)

@Composable
fun NafamaApp() {
    val navController = rememberNavController()
    val simulateurViewModel = SimulateurViewModel()
    val marcheViewModel = MarcheViewModel()

    Scaffold(
        bottomBar = { BarreNavigationBasse(navController) }
    ) { paddingInterne ->
        NavHost(
            navController = navController,
            startDestination = Ecran.Inscription.route,
            modifier = androidx.compose.ui.Modifier.padding(paddingInterne)
        ) {
            composable(Ecran.Inscription.route) {
                InscriptionScreen(onInscrit = { navController.navigate(Ecran.Accueil.route) })
            }
            composable(Ecran.Accueil.route) {
                AccueilScreen(simulateurViewModel, navController)
            }
            composable(Ecran.Pilotage.route) {
                PilotageScreen(simulateurViewModel)
            }
            composable(Ecran.SuiviEnergie.route) {
                SuiviEnergieScreen(simulateurViewModel)
            }
            composable(Ecran.Marche.route) {
                MarcheScreen(marcheViewModel, navController)
            }
            composable(Ecran.AjouterAnnonce.route) {
                AjouterAnnonceScreen(marcheViewModel, navController)
            }
            composable(Ecran.Paiement.route) {
                PaiementScreen()
            }
            composable(Ecran.Carte.route) {
                CarteScreen()
            }
            composable(Ecran.Aide.route) {
                AideScreen()
            }
        }
    }
}

@Composable
private fun BarreNavigationBasse(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destinationActuelle = navBackStackEntry?.destination

    val routesSansBarre = setOf(Ecran.Inscription.route, Ecran.AjouterAnnonce.route, Ecran.Paiement.route)
    val routeActuelle = destinationActuelle?.route
    if (routeActuelle in routesSansBarre) return

    NavigationBar {
        val icones = mapOf(
            Ecran.Accueil.route to Icons.Filled.Home,
            Ecran.Carte.route to Icons.Filled.LocationOn,
            Ecran.Marche.route to Icons.Filled.Storefront,
            Ecran.Aide.route to Icons.Filled.SupportAgent
        )
        ongletsBarreBasse.forEach { ecran ->
            NavigationBarItem(
                selected = destinationActuelle?.hierarchy?.any { it.route == ecran.route } == true,
                onClick = {
                    navController.navigate(ecran.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icones[ecran.route]!!, contentDescription = ecran.label) },
                label = { Text(ecran.label) }
            )
        }
    }
}
