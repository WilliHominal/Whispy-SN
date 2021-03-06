package com.warh.whispy_sn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.warh.whispy_sn.R

@Composable
fun UserView(
    urlProfileImage: String,
    username: String,
    userLocation: String,
    actionIcon: ImageVector,
    normalSize: Boolean? = true,
    onClickAction: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = rememberAsyncImagePainter(urlProfileImage, placeholder = painterResource(R.drawable.placeholder)),
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier =
                if (normalSize!!)
                    Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                else
                    Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
        )
        Column (
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
            Text(
                text = username,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = if (normalSize) 17.sp else 24.sp
            )

            Spacer(modifier = if (normalSize) Modifier.padding(1.dp) else Modifier.padding(4.dp))

            Text(
                text = userLocation,
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = if (normalSize) 12.sp else 17.sp,
                fontStyle = FontStyle.Italic
            )
        }

        IconButton(onClick = { onClickAction() }) {
            Icon(actionIcon, "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserViewPreview(){
    UserView(
        urlProfileImage = "https://cdn-icons-png.flaticon.com/512/206/206881.png",
        username = "myminsta",
        userLocation = "Esperanza, Santa Fe, Argentina",
        actionIcon = Icons.Filled.Remove,
        onClickAction = {}
    )
}