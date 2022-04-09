package com.warh.whispy_sn.routes

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.warh.whispy_sn.ui.screens.AppScaffold
import com.warh.whispy_sn.ui.screens.LoginScreen
import com.warh.whispy_sn.ui.screens.RegisterScreen
import com.warh.whispy_sn.viewmodel.UsersViewModel

sealed class NavigationScreens (var screenRoute: String){
    object Register: NavigationScreens("REGISTER_SCREEN")
    object Login: NavigationScreens("LOGIN_SCREEN")
    object AppScaffold: NavigationScreens("APP_SCAFFOLD")
}

@Composable
fun MainNavGraph(navController: NavHostController, viewModel: UsersViewModel) {

    val auth = FirebaseAuth.getInstance()

    NavHost(navController = navController, startDestination = NavigationScreens.Login.screenRoute){
        composable(route = NavigationScreens.Login.screenRoute){
            LoginScreen(navController)
        }
        composable(route = NavigationScreens.Register.screenRoute){
            RegisterScreen(navController)
        }
        composable(route = "${NavigationScreens.AppScaffold.screenRoute}/{username}"){
            if (auth.currentUser != null) {
                BackHandler(true) {}
            }
            AppScaffold(navController, viewModel, it.arguments?.getString("username") ?: "NONAME")
        }
    }
}