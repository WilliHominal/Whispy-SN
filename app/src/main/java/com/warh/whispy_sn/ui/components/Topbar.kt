package com.warh.whispy_sn.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.warh.whispy_sn.routes.NavigationScreens
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun Topbar(
    navController: NavController,
    username: String
) {
    val auth = FirebaseAuth.getInstance()

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.Black,
        modifier = Modifier.height(28.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = username)
                IconButton(
                    onClick = {
                        var routesString = ""

                        navController.backQueue.forEach {
                            routesString += it.destination.route + ", "
                        }

                        Log.d("TOPBAR",routesString )

                        auth.signOut()

                        navController.navigate(NavigationScreens.Login.screenRoute){
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(Icons.Outlined.Logout, "Logout")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopbarPreview(){
    val navController = rememberNavController()
    WhispySNTheme {
        Topbar(navController, "NONAME")
    }
}