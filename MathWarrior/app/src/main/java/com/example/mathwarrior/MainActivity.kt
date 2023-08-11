package com.example.mathwarrior

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathwarrior.ui.theme.MathWarriorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathWarriorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var problem by remember { mutableStateOf(getProblem()) }
                    Column {
                        MathQuiz(problem.text, problem.answer, problem.wrongChoice1, problem.wrongChoice2, problem.wrongChoice3)
                        Button(onClick = { problem = getProblem() }) {
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MathWarriorTheme {
        Greeting("Android")
    }
}

@Composable
fun MathQuiz(
    text:String,
    answer:Int,
    wrongChoice1:Int,
    wrongChoice2: Int,
    wrongChoice3: Int
) {
    val choices = listOf(answer,wrongChoice1,wrongChoice2,wrongChoice3).shuffled()
    Column() {
        Text(text = text)
        Row() {
            Choice(answer = answer, choice = choices[0])

            Choice(answer = answer, choice = choices[1])
        }
        Row() {
            Choice(answer = answer, choice = choices[2])

            Choice(answer = answer, choice = choices[3])
        }
    }
}

@Composable
fun Choice(answer:Int,choice:Int) {
    Card(
        border = BorderStroke(width = 6.dp, color = Color.Blue),
        modifier = Modifier
            .size(width = 100.dp, height = 50.dp)
            .padding(5.dp)
            .clickable {
            if (answer == choice) {
                //gain energy to use for attacks
            } else {
                //get attacked
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