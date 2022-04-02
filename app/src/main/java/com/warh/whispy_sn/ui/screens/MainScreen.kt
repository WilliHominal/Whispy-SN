package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.warh.whispy_sn.ui.components.Post
import com.warh.whispy_sn.ui.theme.WhispySNTheme

data class PostModel (var urlProfileImage: String, var username: String, var postContent: String, var postImageUrl: String? = null)

@Composable
fun MainScreen() {
    val posts = listOf(
        PostModel(
            "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
            "my_username",
            "Lorem ipsum dolor sit amet, postea platonem democritum no his. Prodesset maiestatis his eu, in sint posse vel. Mel ut mutat rebum tincidunt. Vocent iudicabit reprehendunt eum te.",
            "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
        ),
        PostModel(
            "https://www.pngall.com/wp-content/uploads/5/Profile-Male-PNG.png",
            "useruser_2",
            "Vim at epicuri petentium. Magna insolens interpretaris eam ne, ei porro congue pro. Nec graeci signiferumque ut, sea ut mucius scaevola facilisi. Et usu natum paulo, has indoctum adversarium ad. At eum accusam molestiae, vim meis liber accusamus in. Audire commodo fuisset eam ei.",
        ),
        PostModel(
            "https://cdn-icons-png.flaticon.com/512/206/206881.png",
            "myminsta",
            "Facete delicatissimi te vim, nonumy suavitate ius no. Sit et legere appetere. Eu mei affert sapientem. Clita laoreet quo te.",
            "https://images.newindianexpress.com/uploads/user/imagelibrary/2019/3/7/w900X450/Take_in_the_Scenery.jpg?w=400&dpr=2.6",
        ),
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            items(posts){ postItem ->
                Post(
                    postItem.urlProfileImage,
                    postItem.username,
                    postItem.postContent,
                    postItem.postImageUrl
                )
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