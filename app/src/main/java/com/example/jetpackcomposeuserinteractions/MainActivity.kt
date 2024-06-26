package com.example.jetpackcomposeuserinteractions

import android.os.Bundle
import android.view.MenuItem
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeuserinteractions.ui.theme.JetpackComposeUserInteractionsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeUserInteractionsTheme {

                val snackbarHostState = remember { SnackbarHostState() }
                val txt = remember { mutableStateOf("Hello World") }

                val menuStatus = remember { mutableStateOf(false) }
                val bgColor = remember { mutableStateOf(Color.White) }

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
                    modifier = Modifier.fillMaxSize(),

                    topBar = {
                        TopAppBar(
                            title = { Text("My App", color = Color.White) },
                            navigationIcon = {
                                IconButton(onClick = {
                                    txt.value = "Menu"
                                }) {
                                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Drawer")
                                }
                            },

                            actions = {

                                IconButton(onClick = {
                                    txt.value = "Settings"
                                }) {
                                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                                }

                                IconButton(onClick = {
                                    txt.value = "Search"

                                }) {
                                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                                }

                                IconButton(onClick = {
                                    txt.value = "More"
                                    menuStatus.value = true
                                }) {
                                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "More")

                                    DropdownMenu(expanded = menuStatus.value, onDismissRequest = {
                                        menuStatus.value = false
                                    }) {
                                        DropdownMenuItem(text = { Text("Red") }, onClick = {
                                            txt.value = "Red"
                                            menuStatus.value = false
                                            bgColor.value = Color.Red
                                        })
                                        DropdownMenuItem(text = { Text("Green") }, onClick = {
                                            menuStatus.value = false
                                            txt.value = "Green"
                                            bgColor.value = Color.Green
                                        })
                                        DropdownMenuItem(text = { Text("Blue") }, onClick = {
                                            menuStatus.value = false
                                            txt.value = "Blue"
                                            bgColor.value = Color.Blue
                                        })
                                    }
                                }
                            },

                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Blue,
                                actionIconContentColor = Color.White,
                                navigationIconContentColor = Color.White
                            )
                        )
                    },

                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(color = bgColor.value),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = txt.value, fontSize = 30.sp)
                        }
                    }
                )

//                { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
//                    MyLayout(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding),
//                        snackbarHostState = snackbarHostState
//                    )
//                }
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