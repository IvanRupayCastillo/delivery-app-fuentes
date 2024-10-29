package com.yobel.lecturadeliveryapp.presentation.menu.read_label

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotadev.jetcompose_2024_ii_ecoeats.core.Result
import com.yobel.lecturadeliveryapp.domain.repository.LabelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReadLabelViewModel @Inject constructor(
    val labelRepository: LabelRepository
) : ViewModel() {

    //ESTADOS COMPONENTES
    var state  by mutableStateOf(ReadLabelState())
        private set

    var counter by mutableStateOf(1)
       private set

    var containerData by mutableStateOf("")
        private set

    fun setContainer(containerInfo:String){
        containerData = containerInfo
    }

    fun readLabel(cia:String, user:String, trackId:String, ctr:String, container:String){

        state = state.copy(isLoading = true)

        viewModelScope.launch {

            try{

                val response = withContext(Dispatchers.IO){
                    labelRepository.readLabel(cia,user,trackId,ctr,container)
                }

                when(response){
                    is Result.Error -> {
                        state = state.copy(isLoading = false, error = response.message, success = null)
                    }
                    is Result.Success -> {
                        state = state.copy(isLoading = false, success = response.data, error = null, counter = counter++ )
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
        state = state.copy(error = null, success = null)
    }

}