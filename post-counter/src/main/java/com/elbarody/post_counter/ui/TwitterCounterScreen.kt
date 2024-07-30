package com.elbarody.post_counter.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elbarody.base.compose.AppScaffold
import com.elbarody.base.compose.DefaultButton
import com.elbarody.base.compose.TwitterCounterTopBar
import com.elbarody.base.theme.Blue500
import com.elbarody.base.theme.DarkGray
import com.elbarody.base.theme.DarkGreen
import com.elbarody.base.theme.ErrorHint
import com.elbarody.base.theme.Gray200
import com.elbarody.base.theme.Gray800
import com.elbarody.base.theme.LightBlue
import com.elbarody.base.theme.Typography
import com.elbarody.base.utils.Dimens
import com.elbarody.post_counter.R

@Composable
fun TwitterCounterScreen(modifier: Modifier = Modifier) {
    AppScaffold(
        backgroundColor = Color.White, topBar = {
            TwitterCounterTopBar {}
        }, modifier = modifier.fillMaxSize()
    ) {
        TwitterCounterContent(
            modifier.padding(it),
        )
    }
}

@Composable
fun TwitterCounterContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = Dimens.fourLevelPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(top = Dimens.sixLevelPadding),
            painter = painterResource(id = R.drawable.twitter_logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.padding(top = Dimens.fourLevelPadding))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.fiveLevelPadding)
        ) {
            val charTypedCount by remember {
                mutableIntStateOf(0)
            }

            val remainingCount by remember {
                mutableIntStateOf(280)
            }

            val charTypedText by remember {
                mutableStateOf("$charTypedCount/280")
            }
            CounterItem(stringResource(R.string.characters_typed),charTypedText)

            CounterItem(stringResource(id = R.string.characters_remaining),remainingCount.toString())
        }

        TwitterPostTextField()

        Spacer(modifier = Modifier.padding(top = Dimens.sixLevelPadding))

        ActionButtons()
    }
}

@Composable
fun ActionButtons() {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        DefaultButton(
            text = stringResource(R.string.copy_text),
            onClick = { /*TODO*/ },
            color = DarkGreen,
            minHeight = 40.dp,
            modifier = Modifier.wrapContentSize()
        )

        DefaultButton(
            text = stringResource(R.string.clear_text),
            onClick = { /*TODO*/ },
            color = Color.Red,
            modifier = Modifier.wrapContentSize(),
            minHeight = 40.dp
        )
    }

    Spacer(modifier = Modifier.padding(top = Dimens.sixLevelPadding))

    DefaultButton(
        text = stringResource(R.string.post_tweet),
        onClick = { /*TODO*/ },
        color = Blue500,
        modifier = Modifier.fillMaxWidth(),
        minHeight = 56.dp
    )
}

@Composable
fun TwitterPostTextField() {
    val postText = remember { mutableStateOf(TextFieldValue("")) }

    val focusRequester = remember {
        FocusRequester()
    }

    val isExceedMaxLimit by remember {
        mutableStateOf(false)
    }

    TextField(
        value = postText.value,
        onValueChange = {
            postText.value = it
        },
        placeholder = { TwitterPlaceHolder() },
        modifier = Modifier
            .padding(top = Dimens.sixLevelPadding)
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = Color.White, shape = RoundedCornerShape(Dimens.threeLevelPadding)
            )
            .border(
                width = 1.dp, color = if (isExceedMaxLimit) {
                    ErrorHint
                } else {
                    Gray200
                }, shape = RoundedCornerShape(Dimens.threeLevelPadding)
            )
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(Dimens.threeLevelPadding),
        textStyle = Typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = if (isExceedMaxLimit) {
                ErrorHint
            } else {
                DarkGray
            },
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        )
    )
}

@Composable
fun TwitterPlaceHolder() {
    Text(
        text = stringResource(R.string.typing_hint),
        style = Typography.bodySmall.copy(color = Gray800)
    )
}

@Composable
fun RowScope.CounterItem(title :String,typingValue :String) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(Dimens.threeLevelPadding), color = Color.White)
            .border(
                width = 2.dp,
                color = LightBlue,
                shape = RoundedCornerShape(Dimens.threeLevelPadding)
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = LightBlue, shape = RoundedCornerShape(
                        topEnd = Dimens.threeLevelPadding, topStart = Dimens.threeLevelPadding
                    )
                )
                .padding(vertical = Dimens.twoLevelPadding)
                .fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            style = Typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(com.elbarody.base.R.font.medium))
            )
        )

        Text(
            modifier = Modifier
                .padding(
                    top = Dimens.fourLevelPadding, bottom = Dimens.fiveLevelPadding
                )
                .fillMaxWidth(),
            text = typingValue,
            textAlign = TextAlign.Center,
            style = Typography.titleMedium.copy(fontSize = 26.sp)
        )

    }
}

@Composable
@Preview
fun TwitterCounterScreenPreview() {
    TwitterCounterScreen()
}
