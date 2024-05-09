package org.d3if0006.assesment2_mopro.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if0006.assesment2_mopro.database.OutfitsDb
import org.d3if0006.assesment2_mopro.ui.screen.DetailScreen
import org.d3if0006.assesment2_mopro.ui.screen.MainScreen
import org.d3if0006.assesment2_mopro.ui.screen.MainViewModel
import org.d3if0006.assesment2_mopro.util.ViewModelFactory

@Composable
fun NavigationGraph(navControl: NavHostController = rememberNavController()) {

    val context = LocalContext.current
    val db = OutfitsDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)

    NavHost(
        navController = navControl,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            MainScreen(navControl, viewModel)
        }

        composable(route = Screen.AddOutfits.route) {
            DetailScreen(navControl, viewModel)
        }

        composable(route = Screen.DetailOutfits.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let {
                DetailScreen(navControl, viewModel, it)
            } ?: run {
                DetailScreen(navControl, viewModel)
            }
        }
    }

}