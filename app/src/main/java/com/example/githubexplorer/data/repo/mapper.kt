package com.example.githubexplorer.data.repo

import com.example.githubexplorer.data.dto.DetailUser
import com.example.githubexplorer.domain.model.Repo
import com.example.githubexplorer.domain.model.User

fun com.example.githubexplorer.data.dto.User.toUser() : User {
    return User(
        login = login ,
        id = id ,
        avatarUrl = avatarUrl
    )
}

fun User.toData() : com.example.githubexplorer.data.dto.User {
    return com.example.githubexplorer.data.dto.User(
        login = login ,
        id = id ,
        avatarUrl = avatarUrl ?: ""
    )
}

fun DetailUser.toDetailUser() : com.example.githubexplorer.domain.model.DetailUser{
    return com.example.githubexplorer.domain.model.DetailUser(
        avatar = avatar ,
        name = name ,
        location = location ,
        email = email ,
        followers = followers ,
        following = following ,
        login = login
    )
}


fun com.example.githubexplorer.data.dto.Repo.toUi() : Repo{
    return Repo(
        id = id,
        name = name,
        language = language,
        stars = stars,
        forks = forks,
        htmlUrl = htmlUrl,
    )
}

