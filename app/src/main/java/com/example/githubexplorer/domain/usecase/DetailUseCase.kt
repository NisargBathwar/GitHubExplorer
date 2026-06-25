package com.example.githubexplorer.domain.usecase

import android.util.Log
import com.example.githubexplorer.domain.model.DetailUser
import com.example.githubexplorer.domain.repo.Repository
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(username : String) : DetailUser {
        Log.d("user" , username)
        return repo.detailUser(username)
    }
}