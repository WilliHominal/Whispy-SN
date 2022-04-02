package com.warh.whispy_sn.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.warh.whispy_sn.R
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun Post(
    urlProfileImage: String,
    username: String,
    postContent: String,
    postImageUrl: String? = null,
    withHeader: Boolean? = true
) {
    Card (
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(4.dp, MaterialTheme.colors.primary)
    ) {
        Column {
            if (withHeader == true){
                Row(
                    Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = rememberAsyncImagePainter(urlProfileImage),
                        contentDescription = "Profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Black, CircleShape),
                    )
                    Text(
                        text = username,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 10.dp)
                    )
                    IconButton(onClick = { /*TODO IMPLEMENTAR ACCION*/ }) {
                        Icon(Icons.Filled.Menu, "Post options")
                    }
                }

                Divider(color = MaterialTheme.colors.primary)
            }

            Text(
                text = postContent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            )

            postImageUrl?.let{
                Image(
                    painter = rememberAsyncImagePainter(postImageUrl, placeholder = painterResource(R.drawable.placeholder)),
                    contentDescription = "Post image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PostPreview(){
    WhispySNTheme {
        Post(
            "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
            "my_username",
            "Lorem ipsum jnefudinui dnieuduiej uidjequi jdiquod hoiqwuhdouiqw hduioqw houidqhw iodwhqui dhouqwhudo qwhod uiqhuoid hquiowdh uiq",
        "https://www.pinclipart.com/picdir/middle/355-3553881_stockvader-predicted-adig-user-profile-icon-png-clipart.png",
        )
    }
}