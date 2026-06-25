package com.example.githubexplorer.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph() {

    val vm : UserViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(
        navController ,
        NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route){
            UserUi(
                vm = vm,
                onClick = {id->
                    navController.navigate(
                        NavRoutes.Detail.createRoute(id)
                    )
                }
            )
        }

        composable(NavRoutes.Detail.route ,
            arguments = listOf(navArgument("username"){type = NavType.StringType})){ a->
            val username = a.arguments?.getString("username")!!
            DetailUser(
                vm = vm,
                username = username ,
                onBack = {navController.popBackStack()}
            )
        }
    }

}