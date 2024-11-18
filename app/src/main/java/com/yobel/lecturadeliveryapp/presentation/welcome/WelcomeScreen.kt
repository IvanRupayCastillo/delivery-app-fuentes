package com.yobel.lecturadeliveryapp.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yobel.lecturadeliveryapp.R
import com.yobel.lecturadeliveryapp.presentation.util.Util
import com.yobel.lecturadeliveryapp.ui.theme.Background
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigation:()->Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigation()
    }

    Box(
        modifier = modifier.fillMaxSize().background(color = Background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth().size(60.dp)
        )
    }
}