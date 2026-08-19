package com.sean.jikananimelist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sean.jikananimelist.components.TabBarItem
import com.sean.jikananimelist.network.JAnimeClient
import com.sean.jikananimelist.screens.TopScreen
import com.sean.jikananimelist.ui.theme.JikanAnimeListTheme
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val tabBarItems = listOf(
                TabBarItem.Top
            )
            JikanAnimeListTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            ),
                            title = { Text("Top Anime") }
                        )
                    },
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        BottomAppBar(actions = {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly) {
                                tabBarItems.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentDestination?.hierarchy?.any { item.route == it.route } == true,
                                        onClick = {
                                            navController.navigate(item.route) {

                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }

                                                launchSingleTop = true

                                                restoreState = true
                                            }
                                        },
                                        icon = { Icon(item.image, "${item.title} tab bar item") }
                                    )
                                }
                            }
                        })
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavContainer(navController, innerPadding, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun NavContainer(navController: NavHostController,
                         innerPadding: PaddingValues,
                         modifier: Modifier) {

    NavHost(
        navController,
        startDestination = TabBarItem.Top.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(TabBarItem.Top.route) {
            TopScreen(modifier = Modifier, onItemSelected = { jAnime ->
                navController.navigate("anime_detail/${jAnime.id}")
            })
        }
        composable("anime_detail/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            val animeId = it.arguments?.getInt("id")
            Text("animeId: $animeId")
        }
    }
}
