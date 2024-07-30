package com.elbarody.post_counter

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.elbarody.data.model.TweetRequest
import com.elbarody.data.remote.helper.Response
import com.elbarody.data.repository.PostTweetRepository
import com.elbarody.post_counter.ui.CharCounterViewModel
import com.elbarody.post_counter.ui.TwitterCounterContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CharCounterViewModelTest {

    private lateinit var viewModel: CharCounterViewModel
    private val context: Context = mock(Context::class.java)

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var postTweetRepository: PostTweetRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CharCounterViewModel(context, postTweetRepository)
    }

    @Test
    fun `test updateTweetText with valid input`() {
        val newText = TextFieldValue("Hello World")
        viewModel.handleEvent(TwitterCounterContract.Event.OnTweetTextChanged(newText))

        val state = viewModel.uiState.value
        assert(state.tweetText.text == "Hello World")
        assert(state.characterCount == 11)
        assert(state.remainingCount == 269)
        assert(!state.isCharacterLimitExceeded)
        assert(state.isTweetButtonEnabled)
        assert(state.isCopyButtonEnabled)
        assert(state.isClearButtonEnabled)
        assert(state.errorMessage == null)
    }

    @Test
    fun `test updateTweetText with exceeded character limit`() {
        val newText = TextFieldValue("A".repeat(300))
        viewModel.handleEvent(TwitterCounterContract.Event.OnTweetTextChanged(newText))

        val state = viewModel.uiState.value
        assert(state.tweetText.text == "A".repeat(300))
        assert(state.characterCount == 300)
        assert(state.remainingCount == -20)
        assert(state.isCharacterLimitExceeded)
        assert(!state.isTweetButtonEnabled)
        assert(state.isCopyButtonEnabled)
        assert(state.isClearButtonEnabled)
        assert(state.errorMessage == "Character limit exceeded")
    }

    @Test
    fun `test clearText`() {
        viewModel.handleEvent(TwitterCounterContract.Event.OnClearTextButtonClicked)

        val state = viewModel.uiState.value
        assert(state.tweetText.text.isEmpty())
        assert(state.characterCount == 0)
        assert(state.remainingCount == 280)
        assert(!state.isCharacterLimitExceeded)
        assert(!state.isTweetButtonEnabled)
        assert(!state.isCopyButtonEnabled)
        assert(!state.isClearButtonEnabled)
        assert(state.errorMessage == null)
    }
}
