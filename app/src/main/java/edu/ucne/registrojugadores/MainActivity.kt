package edu.ucne.registrojugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrojugadores.Presentation.Players.Edit.EditPlayerScreen
import edu.ucne.registrojugadores.Presentation.Players.List.PlayerListScreen
import edu.ucne.registrojugadores.ui.theme.RegistroJugadoresTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroJugadoresTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "playerList"
                ) {
                    composable("playerList") {
                        PlayerListScreen(
                            onNavigateToEdit = { id ->
                                navController.navigate("editPlayer/$id")
                            },
                            onNavigateToCreate = {
                                navController.navigate("editPlayer/0")
                            }
                        )
                    }
                    composable("editPlayer/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        EditPlayerScreen(playerId = id)
                    }
                }
            }
        }
    }
}