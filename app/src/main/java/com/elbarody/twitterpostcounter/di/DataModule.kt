package com.elbarody.twitterpostcounter.di

import com.elbarody.data.remote.datasource.IPostTweetDataSource
import com.elbarody.data.remote.datasource.PostTweetDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindTwitterDataSource(twitterRepository: PostTweetDataSource): IPostTweetDataSource
}