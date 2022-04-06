package com.warh.whispy_sn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.warh.whispy_sn.repository.DataProvider
import com.warh.whispy_sn.routes.NavigationGraph
import com.warh.whispy_sn.ui.components.BottomNavigation
import com.warh.whispy_sn.ui.components.Topbar
import com.warh.whispy_sn.ui.screens.RegisterScreen
import com.warh.whispy_sn.ui.theme.WhispySNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhispySNTheme {
                //MainView()
                RegisterScreen()
            }
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    systemUiController.isStatusBarVisible = false

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
        topBar = { Topbar(username = DataProvider.getMyUser().username) },
    ) { innerPadding ->
        NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

