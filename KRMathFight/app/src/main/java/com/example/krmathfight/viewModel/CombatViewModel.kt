package com.example.krmathfight.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krmathfight.utility.Problem
import com.example.krmathfight.utility.getProblem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CombatViewModel : ViewModel() {
    private val _currentProblem = MutableStateFlow(getProblem())
    val currentProblem: StateFlow<Problem> = _currentProblem

    private val _currentTime = MutableStateFlow(600f)
    val currentTime: StateFlow<Float> = _currentTime

    private val timerScope = viewModelScope

    fun startTimer() {
        timerScope.launch {
            while (currentTime.value > 0f) {
                delay(500L)
                _currentTime.value -= 1f
            }
            resetTimer()
        }
    }

    fun resetTimer() {
        _currentTime.value = 600f
        _currentProblem.value = getProblem()
    }
}