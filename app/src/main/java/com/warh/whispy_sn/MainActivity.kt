package com.warh.whispy_sn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.warh.whispy_sn.routes.BottomNavItem
import com.warh.whispy_sn.ui.components.BottomNavigation
import com.warh.whispy_sn.ui.screens.MainScreen
import com.warh.whispy_sn.ui.screens.NewPostScreen
import com.warh.whispy_sn.ui.screens.ProfileScreen
import com.warh.whispy_sn.ui.screens.SearchPeopleScreen
import com.warh.whispy_sn.ui.theme.WhispySNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhispySNTheme {
                MainView()
            }
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
    ) { innerPadding ->
        NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier){
    NavHost(navController, startDestination = BottomNavItem.Home.screenRoute, modifier = modifier) {
        composable(BottomNavItem.Home.screenRoute){
            MainScreen()
        }
        composable(BottomNavItem.Search.screenRoute){
            SearchPeopleScreen()
        }
        composable(BottomNavItem.NewPost.screenRoute){
            NewPostScreen()
        }
        composable(BottomNavItem.Profile.screenRoute){
            ProfileScreen(){}
        }
    }
}