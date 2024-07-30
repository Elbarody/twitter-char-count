package com.elbarody.base.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppScaffold(
    topBar: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topBar,
        modifier = modifier,
        containerColor = backgroundColor,
        contentColor = contentColor,
        content = content
    )
}