package com.example.krmathfight.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterSelection() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row {
                CharacterCard()
                CharacterCard()
                CharacterCard()
            }
            CharacterDisplay()
        }
    }
}

@Composable
fun CharacterDisplay() {
}

@Composable
fun CharacterCard() {

}