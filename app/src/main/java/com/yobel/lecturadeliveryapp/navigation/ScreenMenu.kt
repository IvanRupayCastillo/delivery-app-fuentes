package com.jotadev.jetcompose_2024_ii_ecoeats.navigation

sealed class ScreenMenu(val route:String) {

    object Read : ScreenMenu(route = "read_screen")
    object List : ScreenMenu(route = "list_screen")
    object Sync : ScreenMenu(route = "sync_screen")
}