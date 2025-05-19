package com.example.traq

import CustomTextField
import DropwdownMenu
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.traq.components.Header
import com.example.traq.components.Navbar
import com.example.traq.ui.theme.Blue50
import com.example.traq.ui.theme.TraqTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import route1
import route2
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val db = Firebase.firestore

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

data class Mensaje(
    val nombreUsuario: String = "",
    val fecha: String = "",
    val lineaTransporte: String = "",
    val asunto: String = "",
    val mensaje: String = ""
)


@Composable
private fun AlertsScreenContent() {
    val db = FirebaseFirestore.getInstance()

    val context = LocalContext.current

    var mensajes by remember { mutableStateOf<List<Mensaje>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    var linea by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var mostrarFormulario by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        db.collection("Mensajes").get()
            .addOnSuccessListener { result ->
                mensajes = result.documents.map { doc ->
                    Mensaje(
                        nombreUsuario = doc.getString("nombreUsuario") ?: "",
                        fecha = doc.getString("fecha") ?: "",
                        lineaTransporte = doc.getString("lineaTransporte") ?: "",
                        asunto = doc.getString("asunto") ?: "",
                        mensaje = doc.getString("mensaje") ?: ""
                    )
                }
                cargando = false
            }
            .addOnFailureListener { e ->
                error = e.localizedMessage
                cargando = false
            }
    }
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
            mensajes.forEach { m ->
                AlertCard(
                    usuario = m.nombreUsuario,
                    linea = m.lineaTransporte,
                    asunto = m.asunto,
                    mensaje = m.mensaje,
                    fecha = m.fecha
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
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
                                añadirMensajeFireStore(linea, asunto, mensaje)
                                mostrarFormulario = false

                                context.startActivity(
                                    Intent(
                                        context, AlertsScreen::class.java
                                    )
                                )
                            },
                            enabled = linea.isNotBlank() && asunto.isNotBlank() && mensaje.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Blue50
                            )
                        ) {
                            Text(
                                text = "Enviar", color = Color.White
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
                            text = "Nueva alerta", style = MaterialTheme.typography.titleMedium
                        )
                    }, text = {
                        Column {
                            Column {
                                DropwdownMenu(
                                    opciones = listOf(
                                        "${route1.name} - ${route1.city}",
                                        "${route2.name} - ${route2.city}"
                                    ),
                                    painter = painterResource(id = R.drawable.bus),
                                    onSeleccionChange = { linea = it })
                                DropwdownMenu(
                                    opciones = listOf(
                                        "Accidente", "Atasco", "Avería", "Obras", "Otros"
                                    ),
                                    painter = painterResource(id = R.drawable.alert),
                                    onSeleccionChange = { asunto = it })
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            CustomTextField(onMensajeChange = { mensaje = it })
                        }
                    }, containerColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Navbar()
    }
}


private fun añadirMensajeFireStore(linea: String, asunto: String, mensaje: String) {

    val user = FirebaseAuth.getInstance().currentUser
    val nombreUsuario = user?.displayName

    val formatoFecha = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
    val fecha = formatoFecha.format(Date())

    val mensajeNuevo = hashMapOf(
        "lineaTransporte" to linea,
        "asunto" to asunto,
        "mensaje" to mensaje,
        "nombreUsuario" to nombreUsuario,
        "fecha" to fecha
    )

    db.collection("Mensajes").add(mensajeNuevo).addOnSuccessListener { documentReference ->
        Log.d(
            "Firestore", "Mensaje añadido con id: $documentReference.id"
        )
    }.addOnFailureListener { e ->
        Log.w("Firestore", "No se ha podido guardar el mensaje", e)
    }
}


@Composable
private fun AlertCard(
    usuario: String, linea: String, asunto: String, mensaje: String, fecha: String
) {
    var expandido by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(
                Blue50, RoundedCornerShape(10.dp)
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
                text = usuario, fontWeight = FontWeight.Bold, color = Color.White
            )
            Text(
                text = fecha, fontWeight = FontWeight.Bold, color = Color.White
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
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = linea, color = Color.White
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
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = asunto, color = Color.White
            )
        }
        if (expandido) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}
