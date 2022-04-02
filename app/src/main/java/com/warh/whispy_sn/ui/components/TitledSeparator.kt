package com.warh.whispy_sn.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun TitledSeparator(
    title: String
) {
    Column {
        Text(title)
        Divider(modifier = Modifier.padding(2.dp), color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun TitledSeparatorPreview(){
    WhispySNTheme {
        TitledSeparator(title = "Title")
    }
}