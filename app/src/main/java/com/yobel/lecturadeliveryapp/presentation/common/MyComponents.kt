package com.yobel.lecturadeliveryapp.presentation.common

import android.content.Context
import android.icu.text.CaseMap.Title
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.R
import com.yobel.lecturadeliveryapp.ui.theme.BackgroundAlert
import com.yobel.lecturadeliveryapp.ui.theme.Primary

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    description: String,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = description,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun SpacerComponent(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier)
}

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    color: Color = Color.Unspecified
) {

    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color
    )

}


@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = Primary,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldComponent(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    textLabel: String,
    roundedDp: Dp = 16.dp,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean,
    enabled:Boolean = true,
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = textLabel,
            )
        },
        enabled = enabled,
        shape = RoundedCornerShape(roundedDp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Primary,
            cursorColor = Primary
        ),
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        isError = isError
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    title: String = "",
    imageVector: ImageVector,
    active:Boolean,
    checkPrint:Boolean,
    onIconClick: () -> Unit,
    onLogOutClick:() -> Unit,
    onClickTestPrinter:() -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (title != "") {
                Text(text = title)
            }
        },
        actions = {
            if(checkPrint) {
                IconButton(onClick = { onClickTestPrinter() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_red_ball),
                        contentDescription = "Inactive",
                        modifier = Modifier.padding(end = 8.dp),
                        tint = if (active) Color.Green else Color.Red
                    )
                }
            }
            IconButton(onClick = { onLogOutClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "LogOut",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

        },
        navigationIcon = {
            IconButton(onClick = { onIconClick() }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "navigationIcon",
                )
            }
        }
    )
}

@Composable
fun AlertCustom(
    modifier: Modifier = Modifier,
    title: AnnotatedString,
    content: AnnotatedString,
    dismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { dismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = BackgroundAlert,
                    RoundedCornerShape(8.dp)
                )
                .background(Color(0xFFEBF6ED))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextComponent(
                text = title.text,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
            TextComponent(
                text = content.text,
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
            )
            SpacerComponent(modifier = Modifier.height(8.dp))
            ButtonComponent(
                text = "Aceptar"
            ) {
                dismiss()
            }
        }
    }
}

@Composable
fun LoadingComponent(
    showLoading: Boolean
) {

    if (showLoading) {

        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.progress_animation))

        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable(enabled = false) {}
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun AlertEnterprises(
    modifier: Modifier = Modifier,
    dismiss: () -> Unit,
    data: List<Enterprise>,
    onSelected: (Enterprise?, Boolean) -> Unit,
    onClosed: ()->Unit
) {
    Dialog(
        onDismissRequest = { dismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {

        var enterprise: Enterprise? by remember {
            mutableStateOf(null)
        }
        var checkPrint by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = BackgroundAlert,
                    RoundedCornerShape(8.dp)
                )
                .background(Color(0xFFEBF6ED)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    onClosed()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }
            TextComponent(
                text = "Elegir la compañia",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp
                )
            )

            DropdownMenuComponent(
                options = data,
                onSelected = {
                    enterprise = it
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponent(
                    text = "Imprimir etiqueta",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Checkbox(
                    checked = checkPrint,
                    onCheckedChange = {
                        checkPrint = !checkPrint
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Primary,
                        checkmarkColor = Color.White,
                        uncheckedColor = Primary
                    )
                )
            }


            ButtonComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Aceptar"
            ) {
                onSelected(enterprise, checkPrint)
            }
            SpacerComponent(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuComponent(
    options: List<Enterprise>,
    onSelected: (Enterprise) -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está desplegado o no
    var selectedOption by remember { mutableStateOf("Selecciona una opción") } // Guarda la opción seleccionada
    var selectedDescripcion by remember { mutableStateOf("Selecciona una opción") } // Guarda la opción seleccionada

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }) {
        TextField(
            value = selectedDescripcion,
            onValueChange = { },
            modifier = Modifier
                .menuAnchor(),
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Primary,
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = { Text("Selecciona una opción") }
        )

        // Componente de DropdownMenu
        ExposedDropdownMenu(
            expanded = expanded, // Estado del menú
            onDismissRequest = { expanded = false } // Cierra el menú al hacer clic fuera
        ) {
            options.forEach { option ->

                DropdownMenuItem(text = {
                    Text(text = option.ciaDescripcion)
                }, onClick = {
                    //selectedOption = option.ciaId // Guarda la opción seleccionada
                    selectedDescripcion = option.ciaDescripcion
                    onSelected(option)
                    expanded = false // Cierra el menú
                }
                )
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title:String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                color = Primary,
                fontWeight = FontWeight.Bold
            )
        )
        SpacerComponent(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 64.dp)
                .border(width = 4.dp, color = Color.Black)
        )
    }
}
