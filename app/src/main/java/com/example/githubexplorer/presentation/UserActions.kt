package com.example.githubexplorer.presentation

sealed class UserActions {
    data class GetUsers(val query : String) : UserActions()
    data class QueryChanged(val newQuery : String) : UserActions()
    data class DetailUser(val userName : String) : UserActions()
    object LoadMore : UserActions()
    data class GetRepos(val username : String) : UserActions()
}