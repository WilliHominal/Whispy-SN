package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.R
import com.warh.whispy_sn.ui.components.EditText
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun LoginScreen(){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Image(
                    painter = painterResource(R.drawable.whispy_logo,),
                    "Whispy",
                    Modifier.size(250.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .weight(0.6f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                EditText(
                    value = username,
                    placeholder = "Username",
                    onValueChange = { username = it },
                    hideChars = false
                )
                Spacer(modifier = Modifier.padding(5.dp))
                EditText(
                    value = password,
                    placeholder = "Password",
                    onValueChange = { password = it },
                    hideChars = true
                )
                TextButton(onClick = { /*TODO register onclick action*/ }) {
                    Text(
                        text = "Not a member? Create an account",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body2,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        //TODO login onclick action
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(
                        "LOGIN",
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    WhispySNTheme {
        LoginScreen()
    }
}