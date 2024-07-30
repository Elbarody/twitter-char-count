package com.elbarody.post_counter.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.elbarody.base.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharCounterViewModel @Inject constructor(private val context: Context) :
    BaseViewModel<TwitterCounterContract.Event, TwitterCounterContract.TwitterPostUiState>() {

    override fun createInitialState(): TwitterCounterContract.TwitterPostUiState {
        return TwitterCounterContract.TwitterPostUiState()
    }

    override fun handleEvent(event: TwitterCounterContract.Event) {
        when (event) {
            is TwitterCounterContract.Event.OnTweetTextChanged -> {
                updateTweetText(event.newText)
            }

            is TwitterCounterContract.Event.OnTweetButtonClicked -> {
                postTweet()
            }

            is TwitterCounterContract.Event.OnCopyTextButtonClicked -> {
                copyText()
            }

            is TwitterCounterContract.Event.OnClearTextButtonClicked -> {
                clearText()
            }
        }
    }

    private fun updateTweetText(newText: TextFieldValue) {
        val characterCount = newText.text.length
        val remainingCount = 280 - characterCount
        val isCharacterLimitExceeded = characterCount > 280
        val isTweetButtonEnabled = characterCount in 1..280

        setState {
            copy(
                tweetText = newText,
                characterCount = characterCount,
                remainingCount = remainingCount,
                isCharacterLimitExceeded = isCharacterLimitExceeded,
                isTweetButtonEnabled = isTweetButtonEnabled,
                isCopyButtonEnabled = characterCount > 0,
                isClearButtonEnabled = characterCount > 0,
                errorMessage = if (isCharacterLimitExceeded) "Character limit exceeded" else null
            )
        }
    }

    private fun postTweet() {
    }

    private fun copyText() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Tweet Text", uiState.value.tweetText.text)
        clipboard.setPrimaryClip(clip)
    }

    private fun clearText() {
        setState {
            copy(
                tweetText = TextFieldValue(""),
                characterCount = 0,
                remainingCount = 280,
                isCharacterLimitExceeded = false,
                isTweetButtonEnabled = false,
                isCopyButtonEnabled = false,
                isClearButtonEnabled = false,
                errorMessage = null
            )
        }
    }
}