package com.example.githubexplorer.domain.usecase

import com.example.githubexplorer.domain.repo.Repository
import javax.inject.Inject

class GetRepoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(username : String) = repository.getRepos(username)
}