package com.elbarody.post_counter.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elbarody.base.compose.AppScaffold

@Composable
fun TwitterCounterScreen(modifier: Modifier = Modifier){
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        TwitterCounterContent(
            modifier.padding(it),
        )
    }
}

@Composable
fun TwitterCounterContent(padding: Modifier) {
    Text("Twitter Counter", fontSize = 20.sp)
}

@Composable
@Preview
fun TwitterCounterScreenPreview() {
    TwitterCounterScreen()
}
