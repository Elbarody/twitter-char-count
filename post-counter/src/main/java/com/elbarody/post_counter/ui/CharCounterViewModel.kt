package com.elbarody.post_counter.ui

import com.elbarody.base.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharCounterViewModel @Inject constructor() :
    BaseViewModel<TwitterCounterContract.Event, TwitterCounterContract.State>() {
    override fun createInitialState(): TwitterCounterContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: TwitterCounterContract.Event) {
        TODO("Not yet implemented")
    }
}