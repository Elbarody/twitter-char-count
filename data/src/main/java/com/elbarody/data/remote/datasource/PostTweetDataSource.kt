package com.elbarody.data.remote.datasource

import com.elbarody.data.model.TweetRequest
import com.elbarody.data.model.TweetResponse
import com.elbarody.data.remote.TwitterApi
import com.elbarody.data.remote.helper.Response
import com.elbarody.data.remote.helper.apiCall
import javax.inject.Inject

class PostTweetDataSource @Inject constructor(
    private val api: TwitterApi
) : IPostTweetDataSource {
    override suspend fun postTweet(tweetRequest: TweetRequest): Response<TweetResponse>
    = apiCall {
        api.postTweet(tweetRequest = tweetRequest)
    }
}