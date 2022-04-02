package com.warh.whispy_sn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.ui.components.NewPostCard
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun NewPostScreen() {
    var postText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(15.dp),
    ) {
        NewPostCard(
            postText = postText,
            onPostTextValueChange = { postText = it },
            onUploadImageIconClicked = {  },
            onSendIconClicked = {  }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewPostScreenPreview() {
    WhispySNTheme {
        NewPostScreen()
    }
}