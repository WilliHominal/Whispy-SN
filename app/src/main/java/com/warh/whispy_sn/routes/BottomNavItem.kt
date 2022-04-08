package com.warh.whispy_sn.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.warh.whispy_sn.ui.screens.MainScreen
import com.warh.whispy_sn.ui.screens.NewPostScreen
import com.warh.whispy_sn.ui.screens.ProfileScreen
import com.warh.whispy_sn.ui.screens.SearchPeopleScreen
import com.warh.whispy_sn.viewmodel.UsersViewModel

sealed class BottomNavItem (var title: String, var icon: ImageVector, var screenRoute: String){
    object Home: BottomNavItem("Home", Icons.Filled.Home, "HOME")
    object Search: BottomNavItem("Search", Icons.Filled.Search, "SEARCH")
    object NewPost: BottomNavItem("Post", Icons.Filled.Add, "NEW_POST")
    object Profile: BottomNavItem("Profile", Icons.Filled.AccountCircle, "PROFILE")
}

@Composable
fun NavigationGraph(bottomNavController: NavHostController, modifier: Modifier, viewModel: UsersViewModel){
    NavHost(bottomNavController, startDestination = BottomNavItem.Home.screenRoute, modifier = modifier) {
        composable(BottomNavItem.Home.screenRoute){
            MainScreen()
        }
        composable(BottomNavItem.Search.screenRoute){
            SearchPeopleScreen(viewModel)
        }
        composable(BottomNavItem.NewPost.screenRoute){
            NewPostScreen(viewModel)
        }
        composable(BottomNavItem.Profile.screenRoute){
            ProfileScreen(viewModel){}
        }
    }
}