package com.example.githubexplorer.presentation

import com.example.githubexplorer.domain.model.DetailUser
import com.example.githubexplorer.domain.model.Repo
import com.example.githubexplorer.domain.model.User

data class UserUiState (
    val repos : List<Repo> = emptyList() ,
    val query : String = "" ,
    val detailUser : DetailUser? = null,
    val users : List<User> = emptyList() ,
    val isLoading : Boolean = false ,
    val isLoadingMore : Boolean = false ,
    val error : String? = null
)

