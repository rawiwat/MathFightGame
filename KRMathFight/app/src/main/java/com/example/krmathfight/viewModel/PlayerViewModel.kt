package com.example.krmathfight.viewModel

import android.content.Context
import android.health.connect.datatypes.units.Energy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krmathfight.R
import com.example.krmathfight.utility.Problem
import com.example.krmathfight.utility.getGifDuration
import com.example.krmathfight.utility.getProblem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class IchigoViewModel(
    private var name: String = "Ichigo",
    private var HP: Int = 120,
    private var ATK: Int = 13,
    private var imageID: Int = R.drawable.ichigo_stance,
    private var energy: Int = 0,
    var moveSet: MutableList<Unit> = mutableListOf(),
) : ViewModel() {

    private val _currentHP = MutableStateFlow(HP)
    val currentHP: MutableStateFlow<Int> = _currentHP

    private val _currentATK = MutableStateFlow(ATK)
    val currentATK: MutableStateFlow<Int> = _currentATK

    private val _currentImage = MutableStateFlow(imageID)
    val currentImage: MutableStateFlow<Int> = _currentImage

    private val _currentEnergy = MutableStateFlow(energy)
    val currentEnergy: MutableStateFlow<Int> = _currentEnergy

    fun gainEnergy(amount: Int) {
        currentEnergy.value += amount
    }
    fun getAttacked(enemyAtk: Int) {
        currentHP.value -= enemyAtk
    }

    private fun punch() {
        /*if (currentEnergy.value >= 1) {
            currentEnergy.value -= 1
        }*/
        currentImage.value = R.drawable.ichigo_punch
        viewModelScope.launch {
            delay(1000L)
            currentImage.value = imageID
        }
    }
    private fun addMove(move:Unit) {
        moveSet.add(move)
    }
    init {
        addMove(punch())
    }
}