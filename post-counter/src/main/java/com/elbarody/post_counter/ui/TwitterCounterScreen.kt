package com.elbarody.post_counter.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.elbarody.base.compose.AppScaffold
import com.elbarody.base.compose.DefaultButton
import com.elbarody.base.compose.TwitterCounterTopBar
import com.elbarody.base.constant.Constants
import com.elbarody.base.constant.Constants.MAX_CHAR_COUNT
import com.elbarody.base.theme.Blue300
import com.elbarody.base.theme.Blue500
import com.elbarody.base.theme.DarkGray
import com.elbarody.base.theme.DarkGreen
import com.elbarody.base.theme.ErrorHint
import com.elbarody.base.theme.Gray200
import com.elbarody.base.theme.Gray800
import com.elbarody.base.theme.Green300
import com.elbarody.base.theme.LightBlue
import com.elbarody.base.theme.Red300
import com.elbarody.base.theme.Typography
import com.elbarody.base.utils.Dimens
import com.elbarody.post_counter.R

@Composable
fun TwitterCounterScreen(
    modifier: Modifier = Modifier, viewModel: CharCounterViewModel = hiltViewModel()
) {
    val viewState by viewModel.uiState.collectAsState()

    AppScaffold(
        backgroundColor = Color.White, topBar = {
            TwitterCounterTopBar {}
        }, modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        TwitterCounterContent(
            viewTwitterPostUiState = viewState,
            onTweetTextChanged = {
                viewModel.handleEvent(TwitterCounterContract.Event.OnTweetTextChanged(it))
            },
            onTweetButtonClicked = { viewModel.handleEvent(TwitterCounterContract.Event.OnTweetButtonClicked) },
            onCopyTextButtonClicked = { viewModel.handleEvent(TwitterCounterContract.Event.OnCopyTextButtonClicked) },
            onClearTextButtonClicked = { viewModel.handleEvent(TwitterCounterContract.Event.OnClearTextButtonClicked) },
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TwitterCounterContent(
    viewTwitterPostUiState: TwitterCounterContract.TwitterPostUiState,
    onTweetTextChanged: (TextFieldValue) -> Unit,
    onTweetButtonClicked: () -> Unit,
    onCopyTextButtonClicked: () -> Unit,
    onClearTextButtonClicked: () -> Unit,
    modifier: Modifier
) {
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

            CounterItem(
                stringResource(R.string.characters_typed),
                stringResource(R.string.char_count_format, viewTwitterPostUiState.characterCount, MAX_CHAR_COUNT)
            )

            CounterItem(
                stringResource(id = R.string.characters_remaining),
                viewTwitterPostUiState.remainingCount.toString()
            )
        }

        TwitterPostTextField(
            changedTextField = viewTwitterPostUiState.tweetText,
            isError = viewTwitterPostUiState.isCharacterLimitExceeded,
            onTextChanged = onTweetTextChanged
        )

        Spacer(modifier = Modifier.padding(top = Dimens.sixLevelPadding))

        ActionButtons(
            isCopyEnabled = viewTwitterPostUiState.isCopyButtonEnabled,
            isClearEnabled = viewTwitterPostUiState.isClearButtonEnabled,
            isPostEnabled = viewTwitterPostUiState.isTweetButtonEnabled,
            onCopyClicked = onCopyTextButtonClicked,
            onClearClicked = onClearTextButtonClicked,
            onPostClicked = onTweetButtonClicked
        )
    }
}

@Composable
fun ActionButtons(
    isCopyEnabled: Boolean,
    isClearEnabled: Boolean,
    isPostEnabled: Boolean,
    onCopyClicked: () -> Unit,
    onClearClicked: () -> Unit,
    onPostClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DefaultButton(
            text = stringResource(R.string.copy_text),
            onClick = onCopyClicked,
            color = DarkGreen,
            minHeight = 40.dp,
            modifier = Modifier.wrapContentSize(),
            enabled = isCopyEnabled,
            disabledBackgroundColor = Green300
        )

        DefaultButton(
            text = stringResource(R.string.clear_text),
            onClick = onClearClicked,
            color = Color.Red,
            modifier = Modifier.wrapContentSize(),
            minHeight = 40.dp,
            enabled = isClearEnabled,
            disabledBackgroundColor = Red300
        )
    }

    Spacer(modifier = Modifier.padding(top = Dimens.sixLevelPadding))

    DefaultButton(
        text = stringResource(R.string.post_tweet),
        onClick = onPostClicked,
        color = Blue500,
        modifier = Modifier.fillMaxWidth(),
        minHeight = 56.dp,
        enabled = isPostEnabled,
        disabledBackgroundColor = Blue300
    )
}

@Composable
fun TwitterPostTextField(
    changedTextField: TextFieldValue, isError: Boolean, onTextChanged: (TextFieldValue) -> Unit
) {


    TextField(
        value = changedTextField,
        onValueChange = {
            onTextChanged.invoke(it)
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
                width = 1.dp, color = if (isError) {
                    ErrorHint
                } else {
                    Gray200
                }, shape = RoundedCornerShape(Dimens.threeLevelPadding)
            ),
        shape = RoundedCornerShape(Dimens.threeLevelPadding),
        textStyle = Typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = if (isError) {
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
        text = stringResource(R.string.typing_hint, MAX_CHAR_COUNT),
        style = Typography.bodySmall.copy(color = Gray800)
    )
}

@Composable
fun RowScope.CounterItem(title: String, typingValue: String) {
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
