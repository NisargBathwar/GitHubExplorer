package com.example.githubexplorer.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("total_count")
    val totalCount : Int,
    @SerialName("incomplete_results")
    val incompleteResult : Boolean,
    val items : List<User>
)

@Serializable
data class User(
    val login : String ,
    val id : Int ,
    @SerializedName("avatar_url")
    val avatarUrl : String
)

@Serializable
data class Repo(
    val id : Int ,
    val name : String? ,
    val language : String? ,
    @SerialName("stargazers_count")
    val stars : Int  ,
    @SerialName("forks_count")
    val forks : Int ,
    @SerialName("html_url")
    val htmlUrl : String?
)

@Serializable
data class DetailUser(
    @SerializedName("avatar_url")
    val avatar : String ,
    val login : String ,
    val name : String? ,
    val location : String? ,
    val email : String? ,
    val followers : Int ,
    val following : Int
)