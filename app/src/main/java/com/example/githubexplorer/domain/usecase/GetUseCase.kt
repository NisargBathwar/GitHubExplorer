package com.example.githubexplorer.domain.usecase

import com.example.githubexplorer.domain.model.User
import com.example.githubexplorer.domain.repo.Repository
import javax.inject.Inject

class GetUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(query : String , page : Int) : List<User> {
        return repo.getUsers(query , page)
    }
}

