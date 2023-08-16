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
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.krmathfight.Player
import com.example.krmathfight.R
import com.example.krmathfight.viewModel.CombatViewModel

@Composable
fun CombatScreen(viewModel: CombatViewModel) {
    val samplePlayer by remember {
        mutableStateOf(Player("Kuuga",100,1,"",""))
    }
    val context = LocalContext.current
    val currentProblem by viewModel.currentProblem.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    viewModel.startTimer()
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
            Timer(currentTime, viewModel::startTimer)
            Spacer(modifier = Modifier.height(100.dp))
            MathQuiz(
                text = currentProblem.text,
                choices = choices,
                answer = currentProblem.answer,
                context = context,
                player = samplePlayer
            )
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    add(ImageDecoderDecoder.Factory())
                }.build()

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = R.drawable.kuuga)
                        .apply(block = fun ImageRequest.Builder.() {
                            size(coil.size.Size.ORIGINAL)
                        }).build(),
                    imageLoader = imageLoader),
                contentDescription = null
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
    player: Player
) {

    Box() {
        Column() {
            Text(text = text)
            Row() {
                Choice(answer = answer, choice = choices[0], context,player)

                Choice(answer = answer, choice = choices[1], context,player)
            }
            Row() {
                Choice(answer = answer, choice = choices[2], context,player)

                Choice(answer = answer, choice = choices[3], context,player)
            }
        }
    }
}

@Composable
fun Choice(answer:Int,choice:Int,context: Context,player: Player) {
    Card(
        border = BorderStroke(width = 6.dp, color = Color.Blue),
        modifier = Modifier
            .size(width = 100.dp, height = 50.dp)
            .padding(5.dp)
            .clickable {
                val intent = Intent("get_new_problem")
                context.sendBroadcast(intent)

                if (answer == choice) {
                    player.gainEnergy(1)
                    Toast
                        .makeText(context, "Correct", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //get attacked
                    player.getAttacked()
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
