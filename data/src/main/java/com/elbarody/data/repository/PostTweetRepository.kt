package com.elbarody.data.repository

import com.elbarody.data.model.TweetRequest
import com.elbarody.data.remote.datasource.IPostTweetDataSource
import javax.inject.Inject

class PostTweetRepository @Inject constructor(private val repository: IPostTweetDataSource) {

    suspend operator fun invoke(tweetRequest: TweetRequest) = repository.postTweet(tweetRequest)
}