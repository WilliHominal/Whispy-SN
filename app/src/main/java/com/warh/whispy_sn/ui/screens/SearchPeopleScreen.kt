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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.repository.DataProvider
import com.warh.whispy_sn.ui.components.SearchView
import com.warh.whispy_sn.ui.components.UserView
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun SearchPeopleScreen() {

    var searchText by remember { mutableStateOf("") }

    val users = DataProvider.getOtherUsers()

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

            items(users.filter { userInfo -> userInfo.username.startsWith(searchText) }){ user ->
                UserView(
                    urlProfileImage = user.urlProfileImage,
                    username = user.username,
                    userLocation = "${user.city}, ${user.country}",
                    actionIcon = if (DataProvider.isFriend(user.username)) Icons.Filled.Remove else Icons.Filled.Add
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