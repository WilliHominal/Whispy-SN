package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.repository.DataProvider
import com.warh.whispy_sn.ui.components.FriendView
import com.warh.whispy_sn.ui.components.Post
import com.warh.whispy_sn.ui.components.TitledSeparator
import com.warh.whispy_sn.ui.components.UserView
import com.warh.whispy_sn.ui.theme.WhispySNTheme


@Composable
fun ProfileScreen(
    onEditIconClicked: () -> Unit
) {
    val user = DataProvider.getMyUser()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp).padding(top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            item {
                UserView(
                    urlProfileImage = user.urlProfileImage,
                    username = user.username,
                    userLocation = "${user.city}, ${user.country}",
                    actionIcon = Icons.Filled.Edit,
                    normalSize = false
                ) {
                    onEditIconClicked()
                }
            }

            item {
                TitledSeparator(title = "Friends")
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    items(user.friends) { friend ->
                        val friendTemp = DataProvider.getUserByUsername(friend)
                        FriendView(friendTemp.urlProfileImage, friendTemp.username)
                    }
                }
            }

            item {
                TitledSeparator(title = "Posts")
            }

            items(DataProvider.getMyPosts()){ post ->
                Post(postContent = post.textContent, postImageUrl = post.urlToImage, username = "", urlProfileImage = "", withHeader = false)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    WhispySNTheme {
        ProfileScreen {

        }
    }
}