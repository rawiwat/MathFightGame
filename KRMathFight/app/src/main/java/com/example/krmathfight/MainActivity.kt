package com.example.krmathfight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.krmathfight.compose.CombatScreen
import com.example.krmathfight.compose.Navigator
import com.example.krmathfight.ui.theme.KRMathFightTheme
import com.example.krmathfight.viewModel.CombatViewModel

class MainActivity : ComponentActivity() {

    private val combatViewModel: CombatViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KRMathFightTheme {
                navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigator(
                        navController = navController as NavHostController,
                        combatViewModel = combatViewModel
                    )
                    //CombatScreen(viewModel = combatViewModel)
                }
            }
        }
    }
}
