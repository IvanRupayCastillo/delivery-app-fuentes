package com.yobel.lecturadeliveryapp.presentation.menu.sync

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.yobel.lecturadeliveryapp.domain.repository.LabelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    val labelRepository: LabelRepository
) : ViewModel() {

    //ESTADOS COMPONENTES
    var state  by mutableStateOf(SyncState())
        private set

    //private val _counter = MutableStateFlow(0)
    //val counter = _counter.asStateFlow()

    // Convertimos el Flow en un StateFlow con un valor inicial de 0
    val pendingRealTimeCount: StateFlow<Int> = labelRepository.getCounterPending()
        .stateIn(
            scope = viewModelScope,                     // Contexto coroutine del ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Mantener activo mientras haya observadores
            initialValue = 0                            // Valor inicial
        )

    /*fun getCounter(){
        viewModelScope.launch {
            labelRepository.getCounterPending().onEach {
                _counter.value = it
            }.launchIn(viewModelScope)
        }
    }*/

    fun sync(cia:String, date:String){

        state = state.copy(isLoading = true)

        viewModelScope.launch {

            try{

                val response = withContext(Dispatchers.IO){
                    labelRepository.syncData(cia,date)
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

    fun syncManually(){

        state = state.copy(isLoading = true)

        viewModelScope.launch {

            try{

                val response = withContext(Dispatchers.IO){
                    labelRepository.syncDataManuallyRemote()
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

    fun clearStatus() {
        state = state.copy(success = null, error = null)
    }

}