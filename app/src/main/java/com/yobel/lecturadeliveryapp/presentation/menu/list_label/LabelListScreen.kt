package com.yobel.lecturadeliveryapp.presentation.menu.list_label

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.domain.model.Label
import com.yobel.lecturadeliveryapp.presentation.common.AlertCustom
import com.yobel.lecturadeliveryapp.presentation.common.Header
import com.yobel.lecturadeliveryapp.presentation.common.LoadingComponent
import com.yobel.lecturadeliveryapp.presentation.common.SpacerComponent
import com.yobel.lecturadeliveryapp.presentation.common.TextComponent
import com.yobel.lecturadeliveryapp.presentation.util.Printer
import com.yobel.lecturadeliveryapp.presentation.util.Util
import com.yobel.lecturadeliveryapp.ui.theme.BackgroundCard
import com.yobel.lecturadeliveryapp.ui.theme.Primary

@Composable
fun ListLabelScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    enterprise: Enterprise,
    userName:String,
    userCode:String,
    checkPrint: Boolean,
    viewModel: LabelListViewModel = hiltViewModel()
) {

    val state = viewModel.state

    var showDialog by remember {
        mutableStateOf(false)
    }

    var messageError by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var showDialogPrinter by remember {
        mutableStateOf(false)
    }

    var showDialogValidationPrinter by remember {
        mutableStateOf(false)
    }

    if (showDialogPrinter) {
        AlertCustom(
            title = buildAnnotatedString {
                append("MENSAJE DE NOTIFICACION")
            },
            content = buildAnnotatedString {
                append("Recuerde que para imprimir las etiquetas debe aceptar los permisos")
            },
            dismiss = {
                showDialogPrinter = false
            }
        )
    }

    if (showDialogValidationPrinter) {
        AlertCustom(
            title = buildAnnotatedString {
                append("MENSAJE DE NOTIFICACION")
            },
            content = buildAnnotatedString {
                append("La impresora no esta conectada. Compruebe la conexión")
            },
            dismiss = {
                showDialogValidationPrinter = false
            }
        )
    }

    val requestBluetoothPermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allPermissionsGranted =
            permissions[android.Manifest.permission.BLUETOOTH_CONNECT] == true &&
                    permissions[android.Manifest.permission.BLUETOOTH_SCAN] == true
        if (allPermissionsGranted) {
            //activePrinter = Util.isPrinterConnected(Util.PRINTER_ADDRESS, context)
        } else {
            showDialogPrinter = true
        }
    }

    LoadingComponent(showLoading = viewModel.state.isLoading)

    LaunchedEffect(key1 = state.error) {
        if(state.error != null){
            showDialog = true
            messageError = state.error
        }
        viewModel.clearStatus()
    }

    if (showDialog) {
        AlertCustom(
            title = buildAnnotatedString {
                append("MENSAJE DE NOTIFICACION")
            },
            content = buildAnnotatedString {
                append(messageError)
            },
            dismiss = {
                showDialog = false
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getLabelList(enterprise.ciaId,userCode)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Header(title = "Consulta de Lectura")
        SpacerComponent(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            state?.success?.let { labels ->
                items(labels){ label ->
                    ItemList(
                        label = label,
                        checkPrint = checkPrint,
                        onPrint = {

                            val allPermissionsGranted = ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.BLUETOOTH_CONNECT
                            ) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        android.Manifest.permission.BLUETOOTH_SCAN
                                    ) == PackageManager.PERMISSION_GRANTED

                            if (allPermissionsGranted) {
                                if(Util.isPrinterConnected(Util.PRINTER_ADDRESS, context)) {
                                    Printer.sendZplOverBluetooth(
                                        Util.PRINTER_ADDRESS,
                                        label.sequence,
                                        label.zone1,
                                        label.zone2,
                                        label.route,
                                        label.upload,
                                        label.trackId
                                    )
                                }else{
                                    showDialogValidationPrinter = true
                                }
                            } else {
                                requestBluetoothPermissions.launch(
                                    arrayOf(
                                        android.Manifest.permission.BLUETOOTH_CONNECT,
                                        android.Manifest.permission.BLUETOOTH_SCAN
                                    )
                                )
                            }

                        }
                    )
                }
            }
        }

    }

}

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    label: Label,
    checkPrint:Boolean,
    onPrint:(Label)->Unit
) {

    Card(
        border = BorderStroke(
            width = 1.dp,
            color = BackgroundCard
        ),
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(1.dp)
    ) {
        Column {
            if(checkPrint) {
                IconButton(onClick = { onPrint(label) }) {
                    Icon(
                        imageVector = Icons.Filled.Print,
                        contentDescription = "Print"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal =  8.dp)
            ) {
                TextComponent(
                    modifier = Modifier.weight(2f),
                    text = label.trackId,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                TextComponent(
                    modifier = Modifier.weight(1f),
                    text = label.zone1,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                TextComponent(
                    modifier = Modifier.weight(1f),
                    text = label.zone2,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TextComponent(
                    modifier = Modifier.weight(1f),
                    text = label.sequence,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                TextComponent(
                    modifier = Modifier.weight(1f),
                    text = label.container,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                TextComponent(
                    modifier = Modifier.weight(1f),
                    text = label.route,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = label.upload,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

    }

}