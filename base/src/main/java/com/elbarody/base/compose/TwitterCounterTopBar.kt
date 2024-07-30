package com.elbarody.base.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elbarody.base.R
import com.elbarody.base.theme.LightGray
import com.elbarody.base.theme.Typography
import com.elbarody.base.utils.Dimens

@Composable
fun TwitterCounterTopBar(onBackClick: () -> Unit) {
    Column (modifier = Modifier.padding(top = Dimens.fourLevelPadding).background(Color.White)){
        Row(
            modifier = Modifier
                .padding(Dimens.fourLevelPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Twitter character count",
                style = Typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Image(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onBackClick.invoke()
                },
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null
            )


        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightGray)
        )
    }
}

@Composable
@Preview
fun TwitterTopBarPreview() {
    TwitterCounterTopBar {}
}