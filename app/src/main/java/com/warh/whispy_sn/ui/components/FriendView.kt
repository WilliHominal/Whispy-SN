package com.warh.whispy_sn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.warh.whispy_sn.R
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun FriendView(
    urlProfileImage: String,
    username: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberAsyncImagePainter(urlProfileImage, placeholder = painterResource(R.drawable.placeholder)),
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
        )

        Text(
            text = if (username.length > 8) "${username.substring(0, 6)}..." else username,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendViewPreview(){
    WhispySNTheme {
        FriendView(
            urlProfileImage = "https://cdn-icons-png.flaticon.com/512/206/206881.png",
            username = "myminstas"
        )
    }
}