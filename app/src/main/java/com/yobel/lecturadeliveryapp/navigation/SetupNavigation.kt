package com.jotadev.jetcompose_2024_ii_ecoeats.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.presentation.home.HomeScreen
import com.yobel.lecturadeliveryapp.presentation.sign_in.SignInScreen
import com.yobel.lecturadeliveryapp.presentation.welcome.WelcomeScreen

@Composable
fun SetupNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigation = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignIn.route)
                },
            )
        }

        composable(route = Screen.SignIn.route) {
            SignInScreen(
               onNavigation = { enterprise,checkPrint,userName,userCode ->
                   val enterpriseJson = Gson().toJson(enterprise)
                   val checkPrintPass = if(checkPrint) "1" else "0"
                   navController.popBackStack()
                   navController.navigate(Screen.Home.createRoute(enterpriseJson,checkPrintPass,userName,userCode))
               }
            )
        }
        composable(route = Screen.Home.route) {
            val enterpriseJson = it.arguments?.getString("enterpriseJson")
            val enterprise = Gson().fromJson(enterpriseJson, Enterprise::class.java)
            val checkPrint = it.arguments?.getString("checkPrint")
            val checkPrintPass = if(checkPrint == "1") true else false
            val userName = it.arguments?.getString("userName")
            val userCode = it.arguments?.getString("userCode")
            requireNotNull(enterpriseJson)
            requireNotNull(userName)
            requireNotNull(userCode)
            HomeScreen(
                enterprise = enterprise,
                checkPrint = checkPrintPass,
                userName = userName,
                userCode = userCode,
                onLogOut = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignIn.route)
                }
            )
        }
    }

}