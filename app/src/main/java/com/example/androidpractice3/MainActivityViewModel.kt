package com.example.androidpractice3

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val data: List<Data> = emptyList()
)

class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        var data: List<Data> = FileWriter().readData()
        data = data.reversed()
        _uiState.update { currentState ->
            currentState.copy(data = data)
        }
    }


}