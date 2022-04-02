package com.warh.whispy_sn.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun Topbar(
    username: String
) {
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
    WhispySNTheme {
        Topbar("my_username")
    }
}