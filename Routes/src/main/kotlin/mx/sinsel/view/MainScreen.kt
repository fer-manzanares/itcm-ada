package mx.sinsel.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.sinsel.viewmodel.RouteViewModel

@Composable
@Preview
fun MainScreen(modifier: Modifier = Modifier){
    var viewModel : RouteViewModel = viewModel()
    var text by remember { mutableStateOf("") }
    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var buttonText = "Ir"
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("TSP")
            Spacer(modifier = Modifier.width(8.dp))
            Text(viewModel.graphState.value.toString())
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = from,
                onValueChange = { from = it },
                label = { Text(text = "Inicio") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                val source = viewModel.graphState.value.get(from)
                if (source == null){
                    from = from.toString() + " no existe"
                }else{
                    viewModel.createRoute(source)
                }
            }){
                Text(buttonText)
            }
            Spacer(modifier = Modifier.width(8.dp))

            Text("RUTA")
            Spacer(modifier = Modifier.width(8.dp))
            Text(viewModel.output.value)

        }
    }
}
