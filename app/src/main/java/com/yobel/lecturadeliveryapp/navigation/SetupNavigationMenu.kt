package com.jotadev.jetcompose_2024_ii_ecoeats.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.presentation.menu.list_label.ListLabelScreen
import com.yobel.lecturadeliveryapp.presentation.menu.read_label.ReadLabelScreen

@Composable
fun SetupNavigationMenu(
    navController: NavHostController,
    paddingValues: PaddingValues,
    enterprise:Enterprise,
    userName:String,
    checkPrint:Boolean,
    userCode:String,
    onChangeVisibleBottomBar:(Boolean)->Unit
) {

    NavHost(
        navController = navController,
        startDestination = ScreenMenu.Read.route
    ){
        composable(route = ScreenMenu.Read.route){
            onChangeVisibleBottomBar(true)
            ReadLabelScreen(
                paddingValues = paddingValues,
                enterprise = enterprise,
                userName = userName,
                checkPrint = checkPrint,
                userCode = userCode
            )
        }
        composable(route = ScreenMenu.List.route){
            onChangeVisibleBottomBar(true)
            ListLabelScreen(
                paddingValues = paddingValues,
                enterprise = enterprise,
                userName = userName,
                userCode = userCode,
                checkPrint = checkPrint
            )
        }

    }

}