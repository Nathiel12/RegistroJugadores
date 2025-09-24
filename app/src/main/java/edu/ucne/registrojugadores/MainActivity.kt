package edu.ucne.registrojugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrojugadores.Presentation.Logros.Edit.EditLogroScreen
import edu.ucne.registrojugadores.Presentation.Logros.List.ListLogroScreen
import edu.ucne.registrojugadores.Presentation.Partidas.Edit.EditPartidaScreen
import edu.ucne.registrojugadores.Presentation.Partidas.List.PartidaListScreen
import edu.ucne.registrojugadores.Presentation.Players.Edit.EditPlayerScreen
import edu.ucne.registrojugadores.Presentation.Players.List.PlayerListScreen
import edu.ucne.registrojugadores.Presentation.Partidas.GameScreen
import edu.ucne.registrojugadores.ui.theme.RegistroJugadoresTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroJugadoresTheme {
                val navController = rememberNavController()

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            DrawerContent(navController = navController) {
                                scope.launch { drawerState.close() }
                            }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Registro de Jugadores") },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { scope.launch { drawerState.open() } }
                                    ) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                                    }
                                }
                            )
                        }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = "home"
                            ) {
                                composable("home") {
                                    HomeScreen(navController = navController)
                                }

                                composable("playerList") {
                                    PlayerListScreen(
                                        onNavigateToEdit = { id ->
                                            navController.navigate("editPlayer/$id")
                                        },
                                        onNavigateToCreate = {
                                            navController.navigate("editPlayer/0")
                                        },
                                        onNavigateToPartidas = {
                                            navController.navigate("partidaList")
                                        }
                                    )
                                }

                                composable("editPlayer/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                                    EditPlayerScreen(playerId = id)
                                }

                                composable("partidaList") {
                                    PartidaListScreen(
                                        onNavigateToEdit = { id ->
                                            navController.navigate("editPartida/$id")
                                        },
                                        onNavigateToCreate = {
                                            navController.navigate("editPartida/0")
                                        },
                                        onNavigateToPlayers = {
                                            navController.navigate("playerList")
                                        },
                                        onNavigateToGame = {
                                            navController.navigate("gameScreen")
                                        },
                                        onContinueGame = { partidaId ->
                                            navController.navigate("gameScreen/$partidaId")
                                        }
                                    )
                                }

                                composable("editPartida/{id}") { backStackEntry ->
                                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                                    EditPartidaScreen(partidaId = id)
                                }

                                composable("gameScreen") {
                                    GameScreen(
                                        partidaId = null
                                    )
                                }

                                composable("gameScreen/{partidaId}") { backStackEntry ->
                                    val partidaId = backStackEntry.arguments?.getString("partidaId")?.toIntOrNull()
                                    GameScreen(
                                        partidaId = partidaId
                                    )
                                }

                                composable("logroList") {
                                    ListLogroScreen(
                                        onNavigateToEdit = { logroId ->
                                            navController.navigate("editLogro/$logroId")
                                        },
                                        onNavigateToCreate = {
                                            navController.navigate("editLogro/0")
                                        }
                                    )
                                }

                                composable("editLogro/{logroId}") { backStackEntry ->
                                    val logroId = backStackEntry.arguments?.getString("logroId")?.toIntOrNull()
                                    EditLogroScreen(
                                        logroId = logroId,
                                        onSaveComplete = { navController.popBackStack() },
                                        onDeleteComplete = { navController.popBackStack() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Menú Principal",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Divider()

        NavigationDrawerItem(
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "home",
            onClick = {
                navController.navigate("home")
                onItemClick()
            }
        )

        NavigationDrawerItem(
            label = { Text("Jugadores") },
            selected = navController.currentDestination?.route == "playerList",
            onClick = {
                navController.navigate("playerList")
                onItemClick()
            }
        )

        NavigationDrawerItem(
            label = { Text("Partidas") },
            selected = navController.currentDestination?.route == "partidaList",
            onClick = {
                navController.navigate("partidaList")
                onItemClick()
            }
        )

        NavigationDrawerItem(
            label = { Text("Logros") },
            selected = navController.currentDestination?.route == "logroList",
            onClick = {
                navController.navigate("logroList")
                onItemClick()
            }
        )
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("playerList") },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Registro de Jugadores")
            }

            Button(
                onClick = { navController.navigate("partidaList") },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Registro de Partidas")
            }

            Button(
                onClick = { navController.navigate("logroList") },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Logros")
            }
        }
    }
}