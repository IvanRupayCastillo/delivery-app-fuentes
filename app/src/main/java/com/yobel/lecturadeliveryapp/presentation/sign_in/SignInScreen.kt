package com.yobel.lecturadeliveryapp.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.R
import com.yobel.lecturadeliveryapp.presentation.common.AlertCustom
import com.yobel.lecturadeliveryapp.presentation.common.AlertEnterprises
import com.yobel.lecturadeliveryapp.presentation.common.ButtonComponent
import com.yobel.lecturadeliveryapp.presentation.common.ImageComponent
import com.yobel.lecturadeliveryapp.presentation.common.LoadingComponent
import com.yobel.lecturadeliveryapp.presentation.common.OutlinedTextFieldComponent
import com.yobel.lecturadeliveryapp.presentation.common.SpacerComponent
import com.yobel.lecturadeliveryapp.presentation.common.TextComponent

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigation: (Enterprise?, Boolean, String,String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(false)
    }
    var showDialogValidation by remember {
        mutableStateOf(false)
    }
    var messageValidation by remember {
        mutableStateOf("")
    }
    var showDialogEnterprises by remember {
        mutableStateOf(false)
    }
    var dataEnterprisesList: List<Enterprise> by remember {
        mutableStateOf(emptyList())
    }
    var userName by remember {
        mutableStateOf("")
    }
    var userCode by remember {
        mutableStateOf("")
    }

    LoadingComponent(showLoading = viewModel.state.isLoading)

    LaunchedEffect(key1 = state.success, key2 = state.error, key3 = state.successEnterprise) {
        if (state.success != null) {
            showDialogEnterprises = true
            userName = state.success.name
            dataEnterprisesList = state.success?.enterprises ?: emptyList()
            viewModel.clearStatus()
        }
        if (state.error != null) {
            showDialog = true
            messageValidation = state.error
            viewModel.clearStatus()
        }
    }

    if (showDialog) {
        AlertCustom(
            title = buildAnnotatedString {
                append("MENSAJE DE NOTIFICACION")
            },
            content = buildAnnotatedString {
                append(messageValidation)
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
                append(messageValidation)
            },
            dismiss = {
                showDialogValidation = false
            }
        )
    }

    if (showDialogEnterprises) {
        AlertEnterprises(
            data = dataEnterprisesList,
            dismiss = {
                showDialogValidation = false
            },
            onSelected = { enterprise, checkPrint ->
                if (enterprise == null) {
                    Toast.makeText(context,"Debe seleccionar la empresa",Toast.LENGTH_SHORT).show()
                    return@AlertEnterprises
                }
                showDialogValidation = false
                onNavigation(enterprise, checkPrint, userName, userCode)
            },
            onClosed = {
                showDialogEnterprises = false
                viewModel.clearStatus()
            }
        )
    }


    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SignInHeader()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp),
        ) {
            SignInContent(submit = { user, password ->
                userCode = user
                viewModel.signIn(user, password)
            }, showAlertDialog = { message ->
                showDialogValidation = true
                messageValidation = message
            })
        }


    }

}

@Composable
fun SignInHeader(modifier: Modifier = Modifier) {
    Column {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        ImageComponent(
            image = R.drawable.logo,
            description = "logo",
            modifier = modifier
                .fillMaxWidth()
                .size(50.dp)
        )
        SpacerComponent(modifier = Modifier.height(12.dp))
        ImageComponent(
            image = R.drawable.image_package,
            description = "logo",
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(horizontal = 24.dp),
            contentScale = ContentScale.Crop
        )
    }


}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    submit: (String, String) -> Unit,
    showAlertDialog: (String) -> Unit
) {
    //IRUPAY

    var user by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var visualTransformation by remember {
        mutableStateOf(true)
    }
    val focusManager = LocalFocusManager.current

    TextComponent(
        text = "Login", style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier
    )
    SpacerComponent(modifier = Modifier.height(16.dp))
    OutlinedTextFieldComponent(
        text = user,
        onValueChange = {
            user = it
        },
        textLabel = "Usuario",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        trailingIcon = {
            IconButton(onClick = {
                user = ""
            }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear"
                )
            }
        },
        isError = false
    )
    /*if (viewModel.formState.emailError != null) {
        TextBasic(
            text = viewModel.formState.emailError ?: "",
            style = TextStyle(fontSize = 10.sp),
            color = Color.Red
        )
    }*/
    SpacerComponent(modifier = Modifier.height(8.dp))
    OutlinedTextFieldComponent(
        text = password,
        onValueChange = {
            password = it
        },
        textLabel = "Password",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.clearFocus()
            }
        ),
        trailingIcon = {
            IconButton(onClick = {
                visualTransformation = !visualTransformation
            }) {
                Icon(
                    imageVector = if (visualTransformation) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = "Visibility"
                )
            }
        },
        visualTransformation = if (visualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
        isError = false
    )

    Spacer(modifier = Modifier.height(32.dp))
    ButtonComponent(
        text = "Ingresar",
        onClick = {
            if (user.isEmpty()) {
                showAlertDialog("Debe completar el campo usuario")
                return@ButtonComponent
            }
            if (password.isEmpty()) {
                showAlertDialog("Debe completar el campo contraseña")
                return@ButtonComponent
            }

            submit(user, password)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    )


}

