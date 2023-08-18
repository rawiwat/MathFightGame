package com.example.krmathfight.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.krmathfight.R

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
            CharacterDisplay(LocalContext.current)
        }
    }
}

@Composable
fun CharacterDisplay(context: Context) {
    var currentRider by rememberSaveable {
        mutableIntStateOf(R.drawable.ichigo_stance)
    }

    DisposableEffect(currentRider) {
        val riderReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val riderReceived = intent?.getIntExtra("",0)
                if (riderReceived != null) {
                    currentRider = riderReceived
                }
            }
        }
        context.registerReceiver(riderReceiver, IntentFilter(""))
        onDispose {
            context.unregisterReceiver(riderReceiver)
        }
    }
    Image(painter = painterResource(id = currentRider), contentDescription = null)
}

@Composable
fun CharacterCard() {
Card(Modifier.clickable {  }) {
}
}

@Preview(showBackground = true)
@Composable
fun CharacterDisplayPreview() {
    CharacterDisplay(context = LocalContext.current)
}