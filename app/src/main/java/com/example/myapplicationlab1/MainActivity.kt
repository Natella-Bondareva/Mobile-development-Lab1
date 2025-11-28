package com.example.myapplicationlab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.myapplicationlab1.ui.theme.MyApplicationLab1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationLab1Theme {
                MyApplicationLab1App()
            }
        }
    }
}

@Composable
fun MyApplicationLab1App() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.First) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(it.icon, contentDescription = it.label)
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (currentDestination) {
                AppDestinations.First -> FirstScreen()
                AppDestinations.Second -> SecondScreen()
                AppDestinations.Third -> ThirdScreen()
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    First("remember", Icons.Default.Home),
    Second("saveable", Icons.Default.Favorite),
    Third("ViewModel", Icons.Default.AccountBox),
}

@Composable
fun FirstScreen() {
    var counter by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(32.dp)) {
        Text("remember: $counter")
        Button(onClick = { counter++ }) {
            Text("Add")
        }
    }
}


@Composable
fun SecondScreen() {
    var counter by rememberSaveable { mutableStateOf(0) }

    Column(modifier = Modifier.padding(32.dp)) {
        Text("saveable: $counter")
        Button(onClick = { counter++ }) {
            Text("Add")
        }
    }
}

class MyCounterViewModel : ViewModel() {
    var counter by mutableStateOf(0)
        private set

    fun increment() {
        counter++
    }
}

@Composable
fun ThirdScreen(viewModel: MyCounterViewModel = viewModel()) {
    Column(modifier = Modifier.padding(32.dp)) {
        Text("ViewModel counter: ${viewModel.counter}")
        Button(onClick = { viewModel.increment() }) {
            Text("Add")
        }
    }
}
