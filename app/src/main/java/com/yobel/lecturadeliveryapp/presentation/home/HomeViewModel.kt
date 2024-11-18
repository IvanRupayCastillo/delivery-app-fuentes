package com.yobel.lecturadeliveryapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yobel.lecturadeliveryapp.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val homeRepository: HomeRepository
) : ViewModel() {

    fun saveData(cia:String,user:String){
        viewModelScope.launch {
            homeRepository.saveData(cia,user)
        }

    }

}