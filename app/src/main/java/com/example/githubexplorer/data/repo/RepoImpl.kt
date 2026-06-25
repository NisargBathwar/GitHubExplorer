package com.example.githubexplorer.data.repo

import com.example.githubexplorer.data.remote.GitHubApi
import com.example.githubexplorer.domain.model.Repo
import com.example.githubexplorer.domain.repo.Repository
import javax.inject.Inject

class RepoImpl @Inject constructor(private val api: GitHubApi) : Repository {
    override suspend fun getUsers(query: String , page : Int): List<com.example.githubexplorer.domain.model.User> {
        return api.searchUser(query , page).items.map { it.toUser() }
    }

    override suspend fun detailUser(username: String): com.example.githubexplorer.domain.model.DetailUser {
        return api.detailUser(username).toDetailUser()
    }

    override suspend fun getRepos(username: String): List<Repo> {
        return api.getRepos(username).map { it.toUi() }
    }

}