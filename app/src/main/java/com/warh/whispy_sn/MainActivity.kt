package com.warh.whispy_sn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.warh.whispy_sn.ui.screens.MainScreen
import com.warh.whispy_sn.ui.screens.NewPostScreen
import com.warh.whispy_sn.ui.screens.SearchPeopleScreen
import com.warh.whispy_sn.ui.theme.WhispySNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhispySNTheme {
                //MainScreen()
                //SearchPeopleScreen()
                NewPostScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    WhispySNTheme {
        MainScreen()
    }
}