package com.warh.whispy_sn.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun NewPostCard(
    postText: String,
    onPostTextValueChange: (String) -> Unit,
    onUploadImageIconClicked: () -> Unit,
    onSendIconClicked: () -> Unit,
    imageUri: String? = null,
    imageBitmap: Bitmap? = null
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            //.height(400.dp)
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(20.dp))
            .background(Color.Transparent)
    ) {
        Column {
            OutlinedTextField(
                value = postText,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                onValueChange = onPostTextValueChange,
                maxLines = 10,
                placeholder = { Text("Send a new post to your friends!") },
            )

            imageUri?.let {
                imageBitmap?.let { btm ->

                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = "Post image",
                        modifier = Modifier.padding(10.dp).weight(0.5f),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                IconButton(
                    onClick = onUploadImageIconClicked
                ){
                    Icon(Icons.Filled.AttachFile, "Upload image")
                }

                Text(text = imageUri ?: "Upload image", modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onSendIconClicked
                ){
                    Icon(Icons.Filled.Send, "Send post")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NewPostCardPreview(){
    var postText by remember { mutableStateOf("") }

    WhispySNTheme {
        NewPostCard(
            postText = postText,
            onPostTextValueChange = {postText = it},
            onUploadImageIconClicked = {},
            onSendIconClicked = {}
        )
    }
}