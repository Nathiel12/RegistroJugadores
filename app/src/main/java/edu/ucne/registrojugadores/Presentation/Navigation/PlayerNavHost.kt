package edu.ucne.registrojugadores.Presentation.Navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrojugadores.Presentation.Partidas.Edit.EditPartidaScreen
import edu.ucne.registrojugadores.Presentation.Partidas.List.PartidaListScreen
import edu.ucne.registrojugadores.Presentation.Players.Edit.EditPlayerScreen
import edu.ucne.registrojugadores.Presentation.Players.List.PlayerListScreen
import kotlinx.coroutines.launch

/**@Composable
fun PlayerNavHost(
    navHostController: NavHostController
){
    val scope= rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ){
        NavHost(
            navController = navHostController,
            startDestination = Screen.PlayerList
        ){
            composable<Screen.PlayerList>
        }
    }
}**/