package com.warh.whispy_sn.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.warh.whispy_sn.ui.theme.WhispySNTheme

@Composable
fun EditText(
    value: String,
    placeholder: String,
    enabled: Boolean = true,
    onGloballyPositioned: ((LayoutCoordinates) -> Unit)? = null,
    onValueChange: (String) -> Unit,
    hideChars: Boolean
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.Black),
        label = { Text(placeholder, style = MaterialTheme.typography.body2, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coords ->
                onGloballyPositioned?.let {
                    onGloballyPositioned(coords)
                }
            },
        enabled = enabled,
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        trailingIcon = {
            if (hideChars) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Black,
            focusedLabelColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Gray,
            unfocusedLabelColor = Color.Transparent
        ),
        singleLine = true,
        visualTransformation = if (passwordVisible) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun EditTextPreview(){
    var text by remember { mutableStateOf("") }

    WhispySNTheme {
        EditText(value = text, placeholder = "Username", onValueChange = { text = it }, hideChars = false)
    }
}