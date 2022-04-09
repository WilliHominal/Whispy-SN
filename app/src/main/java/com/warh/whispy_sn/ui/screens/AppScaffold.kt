package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.warh.whispy_sn.routes.NavigationGraph
import com.warh.whispy_sn.ui.components.BottomNavigation
import com.warh.whispy_sn.ui.components.Topbar
import com.warh.whispy_sn.viewmodel.UsersViewModel

@Composable
fun AppScaffold(navController: NavHostController, viewModel: UsersViewModel, username: String) {

    val navControllerBottom = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navControllerBottom) },
        topBar = { Topbar(navController, username) },
    ) { innerPadding ->
        NavigationGraph(bottomNavController = navControllerBottom, modifier = Modifier.padding(innerPadding), viewModel)
    }
}