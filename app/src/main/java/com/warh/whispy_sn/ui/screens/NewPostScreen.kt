package com.warh.whispy_sn.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.ui.components.NewPostCard
import com.warh.whispy_sn.ui.theme.WhispySNTheme
import com.warh.whispy_sn.viewmodel.UsersViewModel

@Composable
fun NewPostScreen(viewModel: UsersViewModel?) {
    var postText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri ->
        imageUri = uri
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(15.dp),
    ) {
        imageUri?.let {
            if(Build.VERSION.SDK_INT < 28){
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
        }
        NewPostCard(
            postText = postText,
            onPostTextValueChange = { postText = it },
            onUploadImageIconClicked = {
                Log.d("NEW_POST_SCREEN", "postText before upload pressed: $postText")
                launcher.launch("image/*")
                Log.d("NEW_POST_SCREEN", "postText after upload pressed: $postText")
            },
            onSendIconClicked = {
                val postTimestamp: String = System.currentTimeMillis().toString()
                if (imageUri != null) {
                    val postImageView = ImageView(context)
                    postImageView.setImageURI(imageUri)
                    viewModel?.addPost(postTimestamp, postText, postImageView)
                } else {
                    viewModel?.addPost(postTimestamp, postText)
                }

                //TODO alert message + clear screen
            },
            imageUri = imageUri?.lastPathSegment.toString(),
            imageBitmap = bitmap.value
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewPostScreenPreview() {
    WhispySNTheme {
        NewPostScreen(null)
    }
}