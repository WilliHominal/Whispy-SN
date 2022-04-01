package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.ui.components.SearchView
import com.warh.whispy_sn.ui.components.UserView
import com.warh.whispy_sn.ui.theme.WhispySNTheme

private data class UserInfo(var urlProfileImage: String, var username: String, var userLocation: String, var actionIcon: ImageVector)

@Composable
fun SearchPeopleScreen() {

    var searchText by remember { mutableStateOf("") }

    val userList = listOf(
        UserInfo(
            urlProfileImage = "https://cdn-icons-png.flaticon.com/512/206/206881.png",
            username = "myminsta",
            userLocation = "Esperanza, Santa Fe, Argentina",
            actionIcon = Icons.Filled.Remove
        ),
        UserInfo(
            urlProfileImage = "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
            username = "my_username",
            userLocation = "my_city, my_state, my_country",
            actionIcon = Icons.Filled.Add
        ),
        UserInfo(
            urlProfileImage = "https://www.pngall.com/wp-content/uploads/5/Profile-Male-PNG.png",
            username = "useruser_2",
            userLocation = "laciudad, laprovincia, elpais",
            actionIcon = Icons.Filled.Add
        ),
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn (
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            item {
                SearchView(
                    searchText = searchText,
                    placeholderText = "Username...",
                    onValueChangeAction = { searchText = it },
                    onClearClick = { searchText = "" }
                )
            }

            items(userList.filter { userInfo -> userInfo.username.startsWith(searchText) }){ user ->
                UserView(
                    urlProfileImage = user.urlProfileImage,
                    username = user.username,
                    userLocation = user.userLocation,
                    actionIcon = user.actionIcon
                ) {
                    //TODO add onclick action
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPeopleScreenPreview(){
    WhispySNTheme {
        SearchPeopleScreen()
    }
}