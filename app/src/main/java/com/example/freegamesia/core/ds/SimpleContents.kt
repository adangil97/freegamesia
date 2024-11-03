package com.example.freegamesia.core.ds

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SimpleErrorContent(
    msgError: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = msgError)
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = onRetry) {
                Text(text = "Reintentar")
            }
        }
    }
}

@Composable
fun SimpleContent(
    msg: String,
    modifier: Modifier = Modifier.fillMaxSize(),
    fontWeight: FontWeight = FontWeight.Bold,
    imageVector: ImageVector = Icons.Default.SearchOff
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .size(160.dp),
            contentDescription = null
        )
        Text(
            msg,
            fontWeight = fontWeight,
            modifier = Modifier.fillMaxWidth(.8f),
            maxLines = 2
        )
    }
}