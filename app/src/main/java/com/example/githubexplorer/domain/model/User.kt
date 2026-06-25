package com.example.githubexplorer.domain.model

import kotlinx.serialization.SerialName

data class User(
    val login : String ,
    val id : Int ,
    val avatarUrl : String?
)


data class Repo(
    val id : Int ,
    val name : String? ,
    val language : String? ,
    val stars : Int  ,
    val forks : Int ,
    val htmlUrl : String?
)

data class DetailUser(
    val login : String ,
    val avatar : String ,
    val name : String? ,
    val location : String? ,
    val email : String? ,
    val followers : Int ,
    val following : Int
)
