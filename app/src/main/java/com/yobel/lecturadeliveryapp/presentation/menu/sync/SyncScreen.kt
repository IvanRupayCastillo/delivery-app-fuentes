package com.yobel.lecturadeliveryapp.presentation.menu.sync

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.presentation.common.AlertCustom
import com.yobel.lecturadeliveryapp.presentation.common.DatePickerWithIcon
import com.yobel.lecturadeliveryapp.presentation.common.Header
import com.yobel.lecturadeliveryapp.presentation.common.LoadingComponent
import com.yobel.lecturadeliveryapp.presentation.common.SpacerComponent
import com.yobel.lecturadeliveryapp.presentation.common.TextComponent
import com.yobel.lecturadeliveryapp.presentation.menu.read_label.HeaderDataBasic
import com.yobel.lecturadeliveryapp.ui.theme.Primary
import com.yobel.lecturadeliveryapp.ui.theme.Secundary
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar

@Composable
fun SynScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    enterprise: Enterprise,
    userName: String,
    userCode: String,
    checkPrint: Boolean,
    viewModel: SyncViewModel = hiltViewModel()
) {

    val state = viewModel.state

    //val counter = viewModel.counter.collectAsState()

    val calendar = Calendar.getInstance()
    val selectedYear = calendar.get(Calendar.YEAR)
    val selectedMonth = calendar.get(Calendar.MONTH)
    val selectedDay = calendar.get(Calendar.DAY_OF_MONTH)

    var date by remember {
        mutableStateOf("${selectedYear}-${String.format("%02d", selectedMonth + 1)}-${String.format("%02d", selectedDay)}")
    }

    var showDialogSuccess by remember {
        mutableStateOf(false)
    }

    var showDialogError by remember {
        mutableStateOf(false)
    }

    val pendingCount by viewModel.pendingRealTimeCount.collectAsState()

    //LaunchedEffect(key1 = Unit) {
    //    viewModel.getCounter()
    //}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(title = "Sincronizacion")
        SpacerComponent(modifier = Modifier.height(16.dp))
        TextComponent(
            text = enterprise.ciaDescripcion,
            style = TextStyle(
                fontSize = 20.sp,
                color = Primary,
                fontWeight = FontWeight.Bold
            ),
            color = Secundary,
        )
        SpacerComponent(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextComponent(
                text = "- Descargue la información del servidor al dispositivo",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )


            DatePickerWithIcon() {
                date = it
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        viewModel.sync(enterprise.ciaId, date)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Primary
                    )
                ) {
                    TextComponent(
                        text = "Descargar",
                        style = TextStyle(
                            fontSize = 16.sp,
                        ),
                    )
                }
            }

            SpacerComponent(modifier = Modifier.height(50.dp))
            TextComponent(
                text = "-Sincronize la informacion del dispositivo al servidor central",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            SpacerComponent(modifier = Modifier.height(16.dp))
            TextComponent(
                text = "*Número de pedidos por sincronizar al servidor: $pendingCount",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            SpacerComponent(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        viewModel.syncManually()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Primary
                    )
                ) {
                    TextComponent(
                        text = "Sincronizar",
                        style = TextStyle(
                            fontSize = 16.sp,
                        ),
                    )
                }
            }

        }




        LoadingComponent(showLoading = viewModel.state.isLoading)

        LaunchedEffect(key1 = state.success, key2 = state.error) {
            if (state.success != null) {
                showDialogSuccess = true
            }

            if (state.error != null) {
                showDialogError = true
            }
        }

        if (showDialogSuccess) {
            AlertCustom(
                title = buildAnnotatedString {
                    append("MENSAJE DE NOTIFICACION")
                },
                content = buildAnnotatedString {
                    append(state.success)
                },
                dismiss = {
                    showDialogSuccess = false
                    viewModel.clearStatus()
                }
            )

        }

        if (showDialogError) {
            AlertCustom(
                title = buildAnnotatedString {
                    append("MENSAJE DE NOTIFICACION")
                },
                content = buildAnnotatedString {
                    append(state.error)
                },
                dismiss = {
                    showDialogError = false
                    viewModel.clearStatus()
                }
            )
        }


    }


}