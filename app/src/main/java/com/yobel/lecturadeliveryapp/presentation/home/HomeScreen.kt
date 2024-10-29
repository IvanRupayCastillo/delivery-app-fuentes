package com.yobel.lecturadeliveryapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.jotadev.jetcompose_2024_ii_ecoeats.navigation.ScreenMenu
import com.jotadev.jetcompose_2024_ii_ecoeats.navigation.SetupNavigationMenu
import com.yobel.lecturadeliveryapp.R
import com.yobel.lecturadeliveryapp.presentation.common.TopBarComponent
import com.yobel.lecturadeliveryapp.ui.theme.Background
import com.yobel.lecturadeliveryapp.ui.theme.Primary

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    enterprise: Enterprise,
    checkPrint: Boolean,
    userName:String,
    userCode:String,
    onLogOut:()->Unit
) {


    val items = listOf(
        BottomNavigationItem(
            title = "Lecturas",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            hasNews = false,
            route = ScreenMenu.Read.route
        ),
        BottomNavigationItem(
            title = "Consulta",
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            hasNews = false,
            route = ScreenMenu.List.route
        ),
    )

    var selectedItemIndex by remember {
        mutableStateOf(0)
    }

    val navController = rememberNavController()

    var bottomBarVisible by remember {
        mutableStateOf(true)
    }

    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
    LaunchedEffect(key1 = currentRoute) {
        selectedItemIndex = items.indexOfFirst { it.route == currentRoute }.takeIf { it != -1 } ?: 0
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarComponent(
                imageVector = if (currentRoute == ScreenMenu.Read.route) Icons.Filled.Home else Icons.Filled.Search,
                onIconClick = {
                    if (!bottomBarVisible) {
                        bottomBarVisible = true
                        navController.popBackStack()
                    }
                },
                onLogOutClick = {
                    onLogOut()
                }
            )
        },
        bottomBar = {

                NavigationBar(
                    containerColor = Color.Transparent,
                    contentColor = Color.Yellow
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = Color.White,
                                unselectedIconColor = Color.Black,
                                disabledIconColor = Color.Black,
                                selectedTextColor = Color.White,
                                disabledTextColor = Color.Black,
                                unselectedTextColor = Color.Black,
                                selectedIndicatorColor = Primary
                            ),
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true

                                }
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (item.badgeCount != null) {
                                            Badge {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        } else if (item.hasNews) {
                                            Badge()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex)
                                            item.selectedIcon
                                        else
                                            item.unSelectedIcon,
                                        contentDescription = item.title,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            }
                        )
                    }
                }
        }
    ) { paddingValues ->

        SetupNavigationMenu(
            navController = navController,
            paddingValues = paddingValues,
            onChangeVisibleBottomBar = {
                bottomBarVisible = it
            },
            enterprise = enterprise,
            userName = userName,
            checkPrint = checkPrint,
            userCode = userCode
        )
    }

}

