package com.traq.app

import TrainTicket
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.traq.components.Header
import com.example.traq.ui.theme.TraqTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.traq.ui.theme.Blue70
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.traq.ui.theme.Blue50
import com.traq.app.components.Navbar

class TrainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraqTheme {
                TrainScreenContent()
            }
        }
    }

    @Composable
    private fun TrainScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header()
            var billetes by remember { mutableStateOf<List<TrainTicket>>(emptyList()) }
            var cargando by remember { mutableStateOf(false) }
            var error by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                cargando = true
                try {
                    val respuesta = RetrofitInstance.api.obtenerBillete()
                    billetes = respuesta
                } catch (e: Exception) {
                    error = "${e.message}"
                    Log.e("API", "Hubieron problemas al recibir la respuesta", e)
                }
                cargando = false
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                when {
                    cargando -> {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Cargando...",
                                color = MaterialTheme.colorScheme.onBackground
                            )

                        }
                    }

                    error != null -> {
                        MostrarError()
                    }

                    else -> {
                        LazyColumn {
                            items(billetes) { billete ->
                                TrainTicketCard(
                                    billete.trayecto,
                                    billete.tren,
                                    billete.fecha,
                                    billete.precio
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                }
            }
            Navbar()
        }
    }
}

@Composable
private fun TrainTicketCard(trayecto: String, tren: String, fecha: String, precio: String) {
    Column(
        modifier = Modifier
            .background(Blue70, RoundedCornerShape(10.dp))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = trayecto,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = fecha,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Operador: $tren",
                color = Color.White
            )
            Text(
                text = "$precio€",
                color = Color.White
            )
        }
    }
}

@Composable
private fun MostrarError() {
    val context = LocalContext.current

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primary,
        onDismissRequest = { },
        title = {
            Text(
                text = "Error de conexión",
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = "Hubo un problema al obtener los datos.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    context.startActivity(
                        Intent(
                            context, TrainScreen::class.java
                        )
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Blue50
                )
            ) {
                Text(text = "Refrescar", color = Color.White)
            }
        }
    )
}
