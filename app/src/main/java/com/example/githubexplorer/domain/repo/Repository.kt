package com.example.githubexplorer.domain.repo

import com.example.githubexplorer.domain.model.DetailUser
import com.example.githubexplorer.domain.model.Repo
import com.example.githubexplorer.domain.model.User

interface Repository {
    suspend fun getUsers(query : String , page : Int) : List<User>
    suspend fun detailUser(username : String) : DetailUser

    suspend fun getRepos(username : String) : List<Repo>
}

