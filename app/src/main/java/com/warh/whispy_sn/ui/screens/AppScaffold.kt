package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.warh.whispy_sn.repository.DataProvider
import com.warh.whispy_sn.routes.NavigationGraph
import com.warh.whispy_sn.ui.components.BottomNavigation
import com.warh.whispy_sn.ui.components.Topbar

@Composable
fun AppScaffold(navController: NavHostController) {

    val navControllerBottom = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navControllerBottom) },
        topBar = { Topbar(username = DataProvider.getMyUser().username, navController) },
    ) { innerPadding ->
        NavigationGraph(bottomNavController = navControllerBottom, modifier = Modifier.padding(innerPadding))
    }
}