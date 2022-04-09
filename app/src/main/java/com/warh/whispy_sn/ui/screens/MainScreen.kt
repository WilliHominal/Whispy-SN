package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.model.PostModel
import com.warh.whispy_sn.model.UserModel
import com.warh.whispy_sn.ui.components.NoDataLoadedText
import com.warh.whispy_sn.ui.components.Post
import com.warh.whispy_sn.ui.theme.WhispySNTheme
import com.warh.whispy_sn.viewmodel.UsersViewModel

@Composable
fun MainScreen(viewModel: UsersViewModel?) {
    var friendsPosts by remember { mutableStateOf<List<PostInfo>>(emptyList()) }

    viewModel?.loadData()

    viewModel?.friendsInfo?.observe(LocalLifecycleOwner.current){
        val auxList =  mutableListOf<PostInfo>()
        it.forEach { friend ->
            friend.posts.forEach { post ->
                auxList.add(PostInfo(post, friend))
            }
        }
        friendsPosts = auxList.sortedWith(postComparator)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp),
        ){
            if (!friendsPosts.isNullOrEmpty()){
                items(friendsPosts){ friendPost ->
                    Post(
                        friendPost.user.urlProfileImage,
                        friendPost.user.username,
                        friendPost.post.textContent,
                        friendPost.post.urlToImage
                    )
                    Spacer(Modifier.padding(15.dp))
                }
            } else {
                item {
                    NoDataLoadedText(text = "Your friends haven't posted anything yet. Add more friends or wait for them to post something!")
                }
            }

        }
    }
}

private val postComparator = Comparator<PostInfo> { a, b ->
    when {
        (a.post.timestamp > b.post.timestamp) -> -1
        else -> 1
    }
}

private data class PostInfo(val post: PostModel, val user: UserModel)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    WhispySNTheme {
        MainScreen(null)
    }
}