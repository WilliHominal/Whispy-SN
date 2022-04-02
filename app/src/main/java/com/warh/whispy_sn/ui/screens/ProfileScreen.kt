package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import com.warh.whispy_sn.ui.components.FriendView
import com.warh.whispy_sn.ui.components.Post
import com.warh.whispy_sn.ui.components.TitledSeparator
import com.warh.whispy_sn.ui.components.UserView
import com.warh.whispy_sn.ui.theme.WhispySNTheme

private data class User (var urlProfileImage: String, var username: String, var city: String, var state: String, var country: String)

@Composable
fun ProfileScreen(
    onEditIconClicked: () -> Unit
) {
    val userTemp = User("https://www.pngall.com/wp-content/uploads/5/Profile-Male-PNG.png","useruser_2", "laciudad", "laprovincia", "elpais")

    val posts = listOf(
        PostModel(
            userTemp.urlProfileImage,
            userTemp.username,
            "Lorem ipsum dolor sit amet, postea platonem democritum no his. Prodesset maiestatis his eu, in sint posse vel. Mel ut mutat rebum tincidunt. Vocent iudicabit reprehendunt eum te.",
            "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
        ),
        PostModel(
            userTemp.urlProfileImage,
            userTemp.username,
            "Vim at epicuri petentium. Magna insolens interpretaris eam ne, ei porro congue pro. Nec graeci signiferumque ut, sea ut mucius scaevola facilisi. Et usu natum paulo, has indoctum adversarium ad. At eum accusam molestiae, vim meis liber accusamus in. Audire commodo fuisset eam ei.",
        ),
        PostModel(
            userTemp.urlProfileImage,
            userTemp.username,
            "Facete delicatissimi te vim, nonumy suavitate ius no. Sit et legere appetere. Eu mei affert sapientem. Clita laoreet quo te.",
            "https://images.newindianexpress.com/uploads/user/imagelibrary/2019/3/7/w900X450/Take_in_the_Scenery.jpg?w=400&dpr=2.6",
        ),
    )

    val friendList = listOf(
        User(urlProfileImage = "https://cdn-icons-png.flaticon.com/512/206/206881.png",
            username = "myminsta",
            city = "Esperanza",
            state = "Santa Fe",
            country = "Argentina"),
        User(urlProfileImage = "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
            username = "my_username",
            city = "my_city",
            state = "my_state",
            country = "my_country"),
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            item {
                UserView(
                    urlProfileImage = userTemp.urlProfileImage,
                    username = userTemp.username,
                    userLocation = "${userTemp.city}, ${userTemp.state}, ${userTemp.country}",
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
                    items(friendList) { friend ->
                        FriendView(urlProfileImage = friend.urlProfileImage, username = friend.username)
                    }
                }
            }

            item {
                TitledSeparator(title = "Posts")
            }

            items(posts){ post ->
                Post(postContent = post.postContent, postImageUrl = post.postImageUrl, username = post.username, urlProfileImage = post.urlProfileImage, withHeader = false)
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