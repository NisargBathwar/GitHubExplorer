package com.example.githubexplorer.di

import com.example.githubexplorer.data.remote.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authroization" , "Bearer ${NetworkConstant.BEARERTOKEN}")
                    .addHeader("Accept" , "application/vnd.github+json")
                    .addHeader("User-Agent" , "GitHubExplorer")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30 , TimeUnit.SECONDS)
            .writeTimeout(30 , TimeUnit.SECONDS)
            .readTimeout(30 , TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : GitHubApi{
        return retrofit.create(GitHubApi::class.java)
    }
}