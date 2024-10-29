package com.jotadev.jetcompose_2024_ii_ecoeats.navigation

sealed class Screen(val route:String) {

    object Welcome : Screen(route = "welcome_screen")
    object SignIn : ScreenMenu(route = "sign_in_screen")
    object Home : Screen(route = "home_screen/?enterpriseJson={enterpriseJson}&checkPrint={checkPrint}&userName={userName}&userCode={userCode}"){
        fun createRoute(enterpriseJson:String, checkPrintPass:String, userName:String, userCode:String) = "home_screen/?enterpriseJson=$enterpriseJson&checkPrint=$checkPrintPass&userName=$userName&userCode=$userCode"
    }

}


