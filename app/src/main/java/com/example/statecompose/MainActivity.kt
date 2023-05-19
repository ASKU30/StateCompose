package com.example.statecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.statecompose.ui.theme.StateComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // Greeting("Android")
                    //HelloContent()
                    //HelloScreen()
                   // HelloScreenVm()
                   // CounterIncrement()
                    //CounterIncrement()
                    //CounterDecrement()
                   // MyUi()
                    HelloCounter()
                }
            }
        }
    }
}


@Composable
fun HelloCounter() {
    var incrementCount by remember {
        mutableStateOf(0)
    }

    var decrementCount by remember {
        mutableStateOf(10)
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(1f)
    ) {
    IncrementCounter(incrementCount) { incrementCount++ }
    DecrementCounter(decrementCount) { decrementCount-- }
    }
}

@Composable
fun IncrementCounter(count: Int, counter: () -> Unit) {
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
            counter()
        }) {
            Text(
                text = "Increment $count",
                style = androidx.compose.ui.text.TextStyle.Default
            )
        }
}

@Composable
fun DecrementCounter(count: Int, counter: () -> Unit) {
        Button(
            modifier = Modifier.padding(10.dp),
            onClick = {
                counter()
        }) {
            Text(
                text = "Decrement $count",
                style = androidx.compose.ui.text.TextStyle.Default
            )
        }
}

//Compose State
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloContent( ) {
    Column(modifier = Modifier.padding(16.dp)) {
        var name: String by rememberSaveable {
            mutableStateOf("")
        }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        OutlinedTextField(
            value = name ,
            onValueChange = {name = it},
            label = { Text("Name")}
        )
    }
}

class HelloViewModel: ViewModel() {
    private val _name: MutableLiveData<String> = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}

@Composable
fun HelloScreenVm(helloViewModel: HelloViewModel = viewModel()) {
    val name: String by helloViewModel.name.observeAsState("")
    HelloContent1(name = name, onNameChanged = {helloViewModel.onNameChange(it)})
}

@Composable
fun HelloScreen() {
    var name: String by rememberSaveable {
        mutableStateOf("")
    }

    HelloContent1(name = name, onNameChanged = {name = it})
}

//State Hoisting
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloContent1(name: String, onNameChanged: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        OutlinedTextField(
            value = name ,
            onValueChange = onNameChanged,
            label = { Text("Name")}
        )
    }
}
@Preview
@Composable
private fun CounterIncrement() {
    var count by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { count++ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Red
            )
        ) {
            Text(text = "Increment Count $count")
        }
    }
}

@Preview
@Composable
private fun CounterDecrement() {
    var count by remember {
        mutableStateOf(10)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(

            onClick = { count-- },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Red
            )
        ) {
            if (count <= 0) {
                count = 0;
            Text(text = "Can't be negative $count")
            } else{
                Text(text = "Decrement Count $count")
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
    StateComposeTheme {
        Greeting("Android")
    }
}