package com.warh.whispy_sn.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warh.whispy_sn.R
import com.warh.whispy_sn.repository.AccountDaoImpl
import com.warh.whispy_sn.routes.NavigationScreens
import com.warh.whispy_sn.ui.components.EditText
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item{
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.whispy_logo),
                        "Whispy",
                        Modifier.size(250.dp)
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    EditText(
                        value = username,
                        placeholder = "Username",
                        onValueChange = { username = it })
                    Spacer(modifier = Modifier.padding(5.dp))
                    EditText(
                        value = password,
                        placeholder = "Password",
                        onValueChange = { password = it },
                        hideChars = true
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    EditText(value = email, placeholder = "Email", onValueChange = { email = it })
                    Spacer(modifier = Modifier.padding(5.dp))
                    EditText(
                        value = country,
                        placeholder = "Country",
                        onValueChange = { country = it })
                    Spacer(modifier = Modifier.padding(5.dp))
                    EditText(value = city, placeholder = "City", onValueChange = { city = it })
                    TextButton(
                        onClick = {
                            navController.navigate(NavigationScreens.Login.screenRoute){
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text(
                            text = "Already a member? Sign in",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.body2,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            AccountDaoImpl().registerAccount(
                                username,
                                email,
                                password,
                                city,
                                country
                            ) { success, user, error ->
                                if (success) {
                                    Toast.makeText(context, "User created", Toast.LENGTH_SHORT)
                                        .show()
                                    Log.d("REGISTER_SCREEN", "User created: ${user!!.email}")
                                    navController.navigate(NavigationScreens.AppScaffold.screenRoute)
                                } else {
                                    Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT)
                                        .show()
                                    Log.d("REGISTER_SCREEN", "Register failed: $error")
                                }
                            }
                            //TODO validate fields before register new accounts
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                    ) {
                        Text(
                            "REGISTER",
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    val navController = rememberNavController()
    WhispySNTheme {
        RegisterScreen(navController)
    }
}