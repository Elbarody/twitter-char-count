package com.elbarody.data.remote

import com.elbarody.data.model.TweetRequest
import com.elbarody.data.model.TweetResponse
import com.elbarody.data.remote.Constants.BEARER_TOKEN
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TwitterApi {

    @POST("1.1/statuses/update.json")
    suspend fun postTweet(
        @Body tweetRequest: TweetRequest
    ): TweetResponse

}