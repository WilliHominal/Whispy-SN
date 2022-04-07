package com.warh.whispy_sn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.warh.whispy_sn.repository.UsersDaoImpl
import com.warh.whispy_sn.routes.MainNavGraph
import com.warh.whispy_sn.ui.theme.WhispySNTheme
import com.warh.whispy_sn.viewmodel.UsersViewModel

class MainActivity : ComponentActivity() {

    val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhispySNTheme {
                MainView(viewModel)
            }
        }
    }
}

@Composable
fun MainView(viewModel: UsersViewModel) {
    val navController = rememberNavController()

    val systemUiController = rememberSystemUiController()

    systemUiController.isStatusBarVisible = false

    MainNavGraph(navController, viewModel)
}

