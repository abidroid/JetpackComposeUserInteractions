package com.example.jetpackcomposeuserinteractions

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeuserinteractions.ui.theme.JetpackComposeUserInteractionsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeUserInteractionsTheme {

                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState) {
                            // for styling snackbar
                            Snackbar(
                                snackbarData = it,
                                containerColor = Color.Red,
                                contentColor = Color.White,
                                actionColor = Color.White,
                                dismissActionContentColor = Color.White

                            )

                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    MyLayout(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        snackbarHostState = snackbarHostState
                    )
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

@Composable
fun MyLayout(name: String, modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {

    // Note - This only works inside a Composable function
    val myContext = LocalContext.current

    // val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val dialogStatus = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            Toast.makeText(myContext, "Hello, I am a Toast message!", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Show Toast")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = {

            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    "Hello, I am a Snackbar message!",
                    actionLabel = "Show Toast",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )

                if (result == SnackbarResult.ActionPerformed) {
                    Toast.makeText(myContext, "Hello, I am a Toast message!", Toast.LENGTH_SHORT).show()
                }
            }
        }) {
            Text(text = "Show Snackbar")
        }

        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            dialogStatus.value = true
        }) {
            Text(text = "Show Dialog")
        }

        // Note: the scope of the dialog matters
        if (dialogStatus.value) {
            AlertDialog(
                title = { Text(text = "Title") },
                text = { Text(text = "Message") },
                onDismissRequest = { dialogStatus.value = false },
                confirmButton = {
                    Button(onClick = {
                        dialogStatus.value = false
                    }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        dialogStatus.value = false
                    }) {
                        Text(text = "Dismiss")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeUserInteractionsTheme {
        //Greeting("Android")
        MyLayout("Android", snackbarHostState = SnackbarHostState())
    }
}