package com.elbarody.post_counter.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.elbarody.base.constant.Constants.MAX_CHAR_COUNT
import com.elbarody.base.helpers.copyTextToClipboard
import com.elbarody.base.mvi.BaseViewModel
import com.elbarody.data.model.TweetRequest
import com.elbarody.data.remote.helper.Response
import com.elbarody.data.repository.PostTweetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharCounterViewModel @Inject constructor(
    private val context: Context, private val postTweetRepository: PostTweetRepository
) : BaseViewModel<TwitterCounterContract.Event, TwitterCounterContract.TwitterPostUiState>() {

    override fun createInitialState(): TwitterCounterContract.TwitterPostUiState {
        return TwitterCounterContract.TwitterPostUiState()
    }

    override fun handleEvent(event: TwitterCounterContract.Event) {
        when (event) {
            is TwitterCounterContract.Event.OnTweetTextChanged -> {
                updateTweetText(event.newText)
            }

            is TwitterCounterContract.Event.OnTweetButtonClicked -> {
                postTweet(TweetRequest(uiState.value.tweetText.text))
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
        val isCharacterLimitExceeded = characterCount > MAX_CHAR_COUNT
        val isTweetButtonEnabled = characterCount in 1..MAX_CHAR_COUNT

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

    private fun postTweet(tweetRequest: TweetRequest) {
        viewModelScope.launch {
            when (val response = postTweetRepository.invoke(tweetRequest)) {
                is Response.Success -> {
                    Toast.makeText(context, "Tweet posted successfully!", Toast.LENGTH_SHORT).show()
                }

                is Response.Error -> {
                    Toast.makeText(
                        context, "Failed to post tweet", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun copyText() {
        copyTextToClipboard(uiState.value.tweetText.text, context)
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