package com.elbarody.post_counter.ui

import com.elbarody.base.mvi.UiEvent
import com.elbarody.base.mvi.UiState

class TwitterCounterContract {

    sealed class Event : UiEvent {
    }

    data class State(
        val tweetState: TweetUiState
    ) : UiState

    sealed class TweetUiState {
        data class TweetTextUpdated(
            val tweetText: String
        ) : TweetUiState()

        data class Error(val errorMessage: String) : TweetUiState()
    }
}
