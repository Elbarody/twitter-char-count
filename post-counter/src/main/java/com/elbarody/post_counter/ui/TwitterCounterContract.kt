package com.elbarody.post_counter.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.elbarody.base.mvi.UiEvent
import com.elbarody.base.mvi.UiState

class TwitterCounterContract {

    sealed class Event : UiEvent {
        data class OnTweetTextChanged(val newText: TextFieldValue) : Event()
        object OnTweetButtonClicked : Event()
        object OnCopyTextButtonClicked : Event()
        object OnClearTextButtonClicked : Event()
    }

    data class TwitterPostUiState(
        val tweetText: TextFieldValue = TextFieldValue(""),
        val characterCount: Int = 0,
        val remainingCount: Int = 280,
        val isCharacterLimitExceeded: Boolean = false,
        val isTweetButtonEnabled: Boolean = false,
        val isCopyButtonEnabled: Boolean = false,
        val isClearButtonEnabled: Boolean = false,
        val errorMessage: String? = null
    ) : UiState
}
