package com.warh.whispy_sn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.warh.whispy_sn.routes.MainNavGraph
import com.warh.whispy_sn.ui.theme.WhispySNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhispySNTheme {
                MainView()
                //RegisterScreen()
                //LoginScreen()
            }
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()
    //val bottomNavHostController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    systemUiController.isStatusBarVisible = false

    MainNavGraph(navController)
}

