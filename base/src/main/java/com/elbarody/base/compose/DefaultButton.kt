package com.elbarody.base.compose

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elbarody.base.theme.Typography
import com.elbarody.base.utils.Dimens

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    color: Color = Blue,
    contentColor: Color = White,
    disabledBackgroundColor: Color = White,
    minHeight: Dp = 40.dp,
) {

    val bgColor = if (enabled) color else disabledBackgroundColor
    Button(
        onClick = { if (enabled) onClick() },
        modifier = modifier.heightIn(min = minHeight),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor, contentColor = contentColor
        ),
        shape = RoundedCornerShape(Dimens.threeLevelPadding)
    ) {
        Text(
            text = text,
            style = Typography.titleLarge.copy(fontSize = 18.sp, color = contentColor),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun DefaultButtonMinHeightPreview() {
    DefaultButton(text = "post", onClick = {})
}