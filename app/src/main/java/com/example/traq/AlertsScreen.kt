package com.example.traq

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.traq.components.Header
import com.example.traq.components.Navbar
import com.example.traq.ui.theme.Blue50
import com.example.traq.ui.theme.TraqTheme
import route1
import route2

class AlertsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraqTheme {
                AlertsScreenContent()
            }
        }
    }
}

@Composable
private fun AlertsScreenContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Header()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
                .verticalScroll(scrollState)
                .fillMaxHeight(),
        ) {
            AlertCard()
            Spacer(modifier = Modifier.height(16.dp))
            AlertCard()
            Spacer(modifier = Modifier.height(16.dp))
            AlertCard()
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(color = Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            var mostrarFormulario by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    mostrarFormulario = !mostrarFormulario
                },
                modifier = Modifier
                    .height(65.dp)
                    .width(65.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue50
                )
            ) {
                Icon(
                    painter = painterResource(com.example.traq.R.drawable.add),
                    contentDescription = "Add icon",
                    tint = Color.White,
                )
            }
            if (mostrarFormulario) {
                AlertDialog(
                    onDismissRequest = { mostrarFormulario = false }, confirmButton = {
                        Button(
                            onClick = {
                                // Aquí puedes manejar la lógica de envío
                                mostrarFormulario = false
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Blue50
                            )
                        ) {
                            Text(
                                text = "Enviar", color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }, dismissButton = {
                        TextButton(
                            onClick = { mostrarFormulario = false },
                        ) {
                            Text(
                                text = "Cancelar", color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }, title = {
                        Text(
                            text = "Nueva alerta",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }, text = {
                        Formulario()
                    },
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Navbar()
    }
}

@Composable
fun Formulario() {
    var input by remember { mutableStateOf("") }

    Column {
        Column {
            MenuDesplegable(
                opciones = listOf("${route1.name} - ${route1.city}", "${route2.name} - ${route2.city}"),
                painter = painterResource(id = R.drawable.bus)
            )
            MenuDesplegable(
                opciones = listOf("Accidente", "Atasco", "Avería", "Obras", "Otros"),
                painter = painterResource(id = R.drawable.alert)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = {
                Text(
                    text = "Introduce tu mensaje...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        )
    }
}

@Composable
fun MenuDesplegable(opciones: List<String>, painter: Painter) {
    var expandido by remember { mutableStateOf(false) }
    var seleccion by remember { mutableStateOf("Escoge una opcion") }

    Column {
        TextButton(onClick = { expandido = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = seleccion,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(com.example.traq.R.drawable.dropdown),
                    contentDescription = "Dropdown icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
            expanded = expandido,
            onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painter,
                                contentDescription = "Bus icon",
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(opcion, color = MaterialTheme.colorScheme.onBackground)
                        }
                    },
                    onClick = {
                        seleccion = opcion
                        expandido = false
                    })
            }
        }
    }
}

@Composable
private fun AlertCard() {
    var expandido by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp)
            )
            .padding(20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) {
                expandido = !expandido
            }, verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Alberto Marian",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "18/05/25 14:00",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.bus),
                contentDescription = "Message icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Interbus Seseña/Madrid", color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.alert),
                contentDescription = "Message icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Accidente durante la ruta", color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (expandido) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "El autobus ha sido chocado por un camión que iba a mas velocidad de la permitida",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
