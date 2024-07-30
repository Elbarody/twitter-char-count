package com.elbarody.twitterpostcounter.di

import com.elbarody.data.remote.Constants
import com.elbarody.data.remote.Constants.OAUTH_CONSUMER_KEY
import com.elbarody.data.remote.Constants.OAUTH_CONSUMER_SECRET
import com.elbarody.data.remote.Constants.OAUTH_TOKEN
import com.elbarody.data.remote.Constants.OAUTH_TOKEN_SECRET
import com.elbarody.data.remote.TwitterApi
import com.elbarody.data.remote.helper.OAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val oauthInterceptor = OAuthInterceptor(
            consumerKey = OAUTH_CONSUMER_KEY,
            consumerSecret = OAUTH_CONSUMER_SECRET,
            accessToken = OAUTH_TOKEN,
            accessTokenSecret = OAUTH_TOKEN_SECRET
        )

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(oauthInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): TwitterApi {
        return retrofit.create(TwitterApi::class.java)
    }
}
