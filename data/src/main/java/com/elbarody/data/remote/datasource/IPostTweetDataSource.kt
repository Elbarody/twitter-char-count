package com.elbarody.data.remote.datasource

import com.elbarody.data.model.TweetRequest
import com.elbarody.data.model.TweetResponse
import com.elbarody.data.remote.helper.Response

interface IPostTweetDataSource {
    suspend fun postTweet(
         tweetRequest: TweetRequest
    ): Response<TweetResponse>
}