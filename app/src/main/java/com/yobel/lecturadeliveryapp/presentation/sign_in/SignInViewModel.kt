package com.yobel.lecturadeliveryapp.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.model.Enterprise
import com.yobel.lecturadeliveryapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val authRepository: AuthRepository
) : ViewModel() {

    //ESTADOS COMPONENTES
    var state  by mutableStateOf(SignInState())
        private set



    fun signIn(user:String, password:String){

        state = state.copy(isLoading = true)

        viewModelScope.launch {

            try{

                val response = withContext(Dispatchers.IO){
                    authRepository.signIn(user,password)
                }

                when(response){
                    is Result.Error -> {
                        state = state.copy(isLoading = false, error = response.message, success = null)
                    }
                    is Result.Success -> {
                        state = state.copy(isLoading = false, success = response.data, error = null)
                    }
                }

            }catch (ex:Exception){
                state = state.copy(error = ex.message, isLoading = false)
            }finally {
                state = state.copy(isLoading = false)
            }


        }

    }

    fun saveEnterprise(enterprise: Enterprise) {
        state = state.copy(isLoading = true)

        viewModelScope.launch {

            try{

                val response = withContext(Dispatchers.IO){
                    authRepository.saveEnterprise(enterprise)
                }

                when(response){
                    is Result.Error -> {
                        state = state.copy(isLoading = false, error = response.message, success = null)
                    }
                    is Result.Success -> {
                        state = state.copy(isLoading = false, successEnterprise = response.data, error = null)
                    }
                }

            }catch (ex:Exception){
                state = state.copy(error = ex.message, isLoading = false)
            }finally {
                state = state.copy(isLoading = false)
            }


        }
    }

    fun clearStatus() {
        state = state.copy(success = null, error = null)
    }

}