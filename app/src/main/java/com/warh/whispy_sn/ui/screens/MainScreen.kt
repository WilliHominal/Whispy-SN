package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.repository.DataProvider
import com.warh.whispy_sn.ui.components.Post
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun MainScreen() {
    val friends = DataProvider.getMyUser().friends

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp),
        ){
            items(friends){ friend ->
                DataProvider.getUserPosts(friend).forEach { post ->
                    Post(
                        DataProvider.getUserByUsername(friend).urlProfileImage,
                        friend,
                        post.textContent,
                        post.urlToImage
                    )
                    Spacer(Modifier.padding(bottom = 15.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    WhispySNTheme {
        MainScreen()
    }
}