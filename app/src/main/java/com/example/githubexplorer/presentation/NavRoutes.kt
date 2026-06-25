package com.example.githubexplorer.presentation

sealed class NavRoutes(val route : String) {
    object Home : NavRoutes("home")
    object Detail : NavRoutes("detail/{username}"){
        fun createRoute(login : String) : String{
            return "detail/$login"
        }
    }
}