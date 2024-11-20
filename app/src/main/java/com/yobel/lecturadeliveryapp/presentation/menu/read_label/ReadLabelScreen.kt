package com.yobel.lecturadeliveryapp.presentation.menu.read_label

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.zxing.integration.android.IntentIntegrator
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.PortraitCaptureActivity
import com.yobel.lecturadeliveryapp.presentation.util.Printer
import com.yobel.lecturadeliveryapp.domain.model.Label
import com.yobel.lecturadeliveryapp.presentation.common.AlertCustom
import com.yobel.lecturadeliveryapp.presentation.common.Header
import com.yobel.lecturadeliveryapp.presentation.common.LoadingComponent
import com.yobel.lecturadeliveryapp.presentation.common.OutlinedTextFieldComponent
import com.yobel.lecturadeliveryapp.presentation.common.SpacerComponent
import com.yobel.lecturadeliveryapp.presentation.common.TextComponent
import com.yobel.lecturadeliveryapp.presentation.util.Util
import com.yobel.lecturadeliveryapp.ui.theme.BackgroundCard
import com.yobel.lecturadeliveryapp.ui.theme.Primary
import com.yobel.lecturadeliveryapp.ui.theme.Secundary
import java.util.Locale

@Composable
fun ReadLabelScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    enterprise: Enterprise,
    userName: String,
    checkPrint: Boolean,
    userCode: String,
    viewModel: ReadLabelViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    var trackId by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var showDialogValidation by remember {
        mutableStateOf(false)
    }

    var messageError by remember {
        mutableStateOf("")
    }

    // Configurar el lanzador de actividad
    val barcodeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val scanResult = IntentIntegrator.parseActivityResult(
                IntentIntegrator.REQUEST_CODE, result.resultCode, intent
            )
            scanResult?.contents?.let {
                // Aquí puedes manejar el resultado del código de barras
                viewModel.readLabel(
                    //cia = enterprise.ciaId,
                    //user = userCode,
                    trackId = it,
                    //ctr = "PE023430",
                    //container = viewModel.containerData
                )
                trackId = ""
            }
        }
    }

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



    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
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

    if (showDialogValidation) {
        AlertCustom(
            title = buildAnnotatedString {
                append("MENSAJE DE NOTIFICACION")
            },
            content = buildAnnotatedString {
                append("Debe ingresar un trackId antes de buscar")
            },
            dismiss = {
                showDialogValidation = false
            }
        )
    }

    LoadingComponent(showLoading = viewModel.state.isLoading)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Header(title = "Lectura de TrackId")
        SpacerComponent(modifier = Modifier.height(16.dp))
        HeaderDataBasic(
            enterprise = enterprise,
            userName = userName,
            container = viewModel.containerData,
            onContainerChange = {
                viewModel.setContainer(it)
            },
        )
        SpacerComponent(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                text = "Escanear etiqueta:",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(onClick = {
                /*val intent = IntentIntegrator(context as Activity)
                    .setCaptureActivity(PortraitCaptureActivity::class.java)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    .setPrompt("Escanea un código de barras")
                    .setOrientationLocked(true)
                    .setBeepEnabled(false)
                    .createScanIntent()

                barcodeLauncher.launch(intent)*/

                val integrator = IntentIntegrator(context as Activity)
                    .setCaptureActivity(PortraitCaptureActivity::class.java) // Actividad personalizada
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    .setPrompt("Escanea un código de barras")
                    .setOrientationLocked(true)
                    .setBeepEnabled(false)

                // Lanzar el escáner con barcodeLauncher
                barcodeLauncher.launch(integrator.createScanIntent())
            }) {
                Icon(
                    imageVector = Icons.Filled.QrCode,
                    contentDescription = "Search",
                    modifier = Modifier.size(32.dp)
                )
            }

        }

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextFieldComponent(
                text = trackId,
                onValueChange = {
                    trackId = it
                },
                textLabel = "Track Id (Leer código)",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                isError = false,
                modifier = Modifier.weight(2f)
            )
            SpacerComponent(modifier = Modifier.width(8.dp))
            OutlinedTextFieldComponent(
                text = "${state.counter}",
                onValueChange = {

                },
                enabled = false,
                textLabel = "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                isError = false,
                modifier = Modifier.weight(0.5f)
            )
            IconButton(onClick = {
                if (trackId.isEmpty()) {
                    showDialogValidation = true
                    return@IconButton
                }
                viewModel.readLabel(
                    //cia = enterprise.ciaId,
                    //user = userCode,
                    trackId = trackId,
                    //ctr = "PE023430",
                    //container = viewModel.containerData
                )
                trackId = ""
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.weight(1f)
                )
            }


        }
        SpacerComponent(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 16.dp)

        )

        state.success?.let { label ->
            CardLabel(
                label = label
            )
            if(checkPrint) {
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
                            label.trackId,
                            label.date
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
        }


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val text = if (checkPrint) "*Con impresión de etiquetas" else ""
            TextComponent(
                text = text, style = TextStyle(
                    fontSize = 18.sp,
                    color = Primary
                )
            )
        }


    }
}

@Composable
fun CardLabel(
    modifier: Modifier = Modifier,
    label: Label
) {

    Card(
        border = BorderStroke(
            width = 1.dp,
            color = BackgroundCard
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(1.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            RowCardLabel(title = "Secuencia", content = label.sequence)
            SpacerComponent(modifier = Modifier.height(4.dp))
            RowCardLabel(title = "Zona1", content = label.zone1)
            SpacerComponent(modifier = Modifier.height(4.dp))
            RowCardLabel(title = "Zona2", content = label.zone2)
            SpacerComponent(modifier = Modifier.height(4.dp))
            RowCardLabel(title = "Ruta", content = label.route)
            SpacerComponent(modifier = Modifier.height(4.dp))
            RowCardLabel(title = "Carga", content = label.upload)
            SpacerComponent(modifier = Modifier.height(4.dp))
            RowCardLabel(title = "TrackId", content = label.trackId)

        }

    }
}

@Composable
fun RowCardLabel(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = "$title:",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.weight(1f)
        )
        TextComponent(
            text = content,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Secundary
            ),
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun RowCardLabelSingle(
    modifier: Modifier = Modifier,
    content: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = content,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun HeaderDataBasic(
    modifier: Modifier = Modifier,
    enterprise: Enterprise,
    userName: String,
    container: String,
    onContainerChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = enterprise.ciaDescripcion,
            style = TextStyle(
                fontSize = 20.sp,
                color = Primary,
                fontWeight = FontWeight.Bold
            ),
            color = Secundary,
        )
        SpacerComponent(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TextComponent(
                text = "Empleado:",
                style = TextStyle(
                    fontSize = 17.sp,
                    color = Primary,
                    fontWeight = FontWeight.Light
                ),
                color = Color.Black,
                modifier = Modifier.weight(1.2f)
            )
            TextComponent(
                text = userName,
                style = TextStyle(
                    fontSize = 17.sp,
                    color = Primary,
                    fontWeight = FontWeight.Light
                ),
                color = Color.Black,
                modifier = Modifier.weight(3f)
            )
        }
        SpacerComponent(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextComponent(
                text = "Contenedor:",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Primary,
                    fontWeight = FontWeight.Light
                ),
                color = Color.Black,
                modifier = Modifier.weight(1.2f)
            )
            SpacerComponent(modifier = Modifier.width(8.dp))
            OutlinedTextFieldComponent(
                text = container,
                onValueChange = {
                    onContainerChange(it.uppercase(Locale.getDefault()))
                },
                textLabel = "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                isError = false,
                modifier = Modifier.weight(3f)
            )
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                },
                modifier = Modifier.weight(0.5f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Check",
                    tint = Color.Green,
                )
            }
        }
    }
}

