package com.example.githubexplorer.data.remote

import com.example.githubexplorer.data.dto.DetailUser
import com.example.githubexplorer.data.dto.Repo
import com.example.githubexplorer.data.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q : String  ,
        @Query("page") page : Int  ,
        @Query("per_page") perPage : Int = 20
    )  : UserResponse

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username : String) : List<Repo>
    @GET("users/{username}")
    suspend fun detailUser(@Path("username") username : String) : DetailUser
}