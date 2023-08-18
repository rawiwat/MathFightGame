package com.example.krmathfight.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.krmathfight.CharacterModel.Enemy
import com.example.krmathfight.CharacterModel.KamenRider
import com.example.krmathfight.R
import com.example.krmathfight.viewModel.CombatViewModel
import com.example.krmathfight.viewModel.IchigoViewModel
import com.example.krmathfight.viewModel.KamenRiderViewModel

@Composable
fun CombatScreen(viewModel: CombatViewModel) {
    val context = LocalContext.current
    val samplePlayerViewModel = IchigoViewModel()
    val samplePlayer by samplePlayerViewModel.currentKamenRider.collectAsState()
    val sampleEnemy = Enemy(name = "Shocker",R.drawable.ichigo_stance,1,1)
    val currentProblem by viewModel.currentProblem.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val choices = remember(currentProblem.answer) {
        listOf(
            currentProblem.answer,
            currentProblem.wrongChoice1,
            currentProblem.wrongChoice2,
            currentProblem.wrongChoice3
        ).shuffled()
    }

    DisposableEffect(context) {
        val intentFilter = IntentFilter("get_new_problem")
        val problemReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                viewModel.resetTimer()
            }
        }
        context.registerReceiver(problemReceiver, intentFilter)

        onDispose {
            context.unregisterReceiver(problemReceiver)
        }
    }

    Surface(Modifier.fillMaxSize()) {
        Column {
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    add(ImageDecoderDecoder.Factory())
                }.build()

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(samplePlayer.imageID)
                        .apply(block = fun ImageRequest.Builder.() {
                            size(500)
                        }).build(),
                    imageLoader = imageLoader),
                contentDescription = null
            )
            Button(onClick = { samplePlayer.moveSet[0].function(samplePlayer,context) }) {
                Text("Punch")
            }

            Timer(currentTime, viewModel::startTimer)
            Spacer(modifier = Modifier.height(100.dp))
            MathQuiz(
                text = currentProblem.text,
                choices = choices,
                answer = currentProblem.answer,
                context = context,
                player = samplePlayerViewModel,
                enemy = sampleEnemy
            )


        }
    }
}

@Composable
fun MathQuiz(
    text:String,
    choices: List<Int>,
    answer:Int,
    context: Context,
    player: ViewModel,
    enemy: Enemy
) {

    Box() {
        Column() {
            Text(text = text)
            Row() {
                Choice(answer = answer, choice = choices[0], context, player, enemy)

                Choice(answer = answer, choice = choices[1], context, player, enemy)
            }
            Row() {
                Choice(answer = answer, choice = choices[2], context, player, enemy)

                Choice(answer = answer, choice = choices[3], context, player, enemy)
            }
        }
    }
}

@Composable
fun Choice(answer:Int,choice:Int,context: Context,player: ViewModel, enemy:Enemy) {
    Card(
        border = BorderStroke(width = 6.dp, color = Color.Blue),
        modifier = Modifier
            .size(width = 100.dp, height = 50.dp)
            .padding(5.dp)
            .clickable {
                val intent = Intent("get_new_problem")
                context.sendBroadcast(intent)

                if (answer == choice) {
                    Toast
                        .makeText(context, "Correct", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //get attacked
                    Toast
                        .makeText(context, "Wrong", Toast.LENGTH_SHORT)
                        .show()
                }

            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = choice.toString(),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Timer(currentTime: Float,timerStart:() -> Unit) {
    val maxWidth = 600f
    val currentWidth = (maxWidth * (currentTime / 600f)).coerceIn(0f, maxWidth)
    val height = 100f
    val barModifier = Modifier
    val offset = Offset.Zero
    if (currentWidth == maxWidth) {
        timerStart()
    }

    Canvas(
        modifier = barModifier
    ) {
        drawRect(
            topLeft = offset,
            color = Color.Black,
            size = Size(maxWidth, height)
        )
        drawRect(
            topLeft = offset,
            color = Color.Red,
            size = Size(currentWidth, height)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CombatScreenPreview() {
    CombatScreen(viewModel = CombatViewModel())
}