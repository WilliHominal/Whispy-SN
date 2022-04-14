package com.warh.whispy_sn.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.model.UserModel
import com.warh.whispy_sn.ui.components.*
import com.warh.whispy_sn.ui.theme.WhispySNTheme
import com.warh.whispy_sn.viewmodel.UsersViewModel

@Composable
fun ProfileScreen(
    viewModel: UsersViewModel?,
) {
    var user by remember { mutableStateOf<UserModel?>(null) }
    var friendsInfo by remember { mutableStateOf<List<UserModel>>(emptyList()) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri ->
        imageUri = uri
    }

    viewModel?.myInfo?.observe(LocalLifecycleOwner.current){
        user = it
    }

    viewModel?.friendsInfo?.observe(LocalLifecycleOwner.current){
        friendsInfo = it
    }

    viewModel?.loadData()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        imageUri?.let {
            if(Build.VERSION.SDK_INT < 28){
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            val profileImageView = ImageView(context)
            profileImageView.setImageURI(imageUri)
            viewModel?.editProfile(profileImageView)

            imageUri = null
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            item {
                UserView(
                    urlProfileImage = user?.urlProfileImage ?: "",
                    username = user?.username ?: "NO_NAME",
                    userLocation = "${user?.city}, ${user?.country}",
                    actionIcon = Icons.Filled.Edit,
                    normalSize = false
                ) {
                    launcher.launch("image/*")
                }
            }

            item {
                TitledSeparator(title = "Friends")
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    items(friendsInfo) { friend ->
                        FriendView(urlProfileImage = friend.urlProfileImage, username = friend.username)
                    }
                }
            }

            item {
                TitledSeparator(title = "Posts")
            }

            user?.let{
                if (it.posts.isEmpty()){
                    item {
                        NoDataLoadedText("You havent posted anything yet. Try to post something now!")
                    }
                } else {
                    items(it.posts){ post ->
                        Post(postContent = post.textContent, postImageUrl = post.urlToImage, username = "", urlProfileImage = "", withHeader = false)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    WhispySNTheme {
        ProfileScreen(null)
    }
}