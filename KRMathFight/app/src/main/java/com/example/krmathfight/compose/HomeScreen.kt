package com.example.krmathfight.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.krmathfight.R
import com.example.krmathfight.ui.theme.KRMathFightTheme

@Composable
fun HomeScreen(navController: NavController) {
    Surface(Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(route = "Combat") },
                modifier = Modifier.size(height = 50.dp, width = 200.dp)
            ) {
                Text(text = "Start")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate(route = "Combat") },
                modifier = Modifier.size(height = 50.dp, width = 200.dp)
            ) {
                Text(text = "Upgrade")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate(route = "Setting") },
                modifier = Modifier.size(height = 50.dp, width = 200.dp)
            ) {
                Text(text = "Setting")
            }

        }
    }
}
  
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    KRMathFightTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}