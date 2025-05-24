package com.example.traq

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.traq.ui.theme.Blue50
import com.example.traq.ui.theme.TraqTheme
import com.google.firebase.auth.FirebaseAuth

class RegisterScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraqTheme {
                RegisterScreenContent()
            }
        }
    }
}

@Composable
private fun RegisterScreenContent() {
    val context = LocalContext.current
    var nombreUsuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }

    var usuarioActual = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registrate",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(42.dp))
        Text(
            text = "Nombre de usuario", color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = nombreUsuario, onValueChange = { nombreUsuario = it }, placeholder = {
                Text(
                    text = "Introduce un nombre de usuario",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }, singleLine = true, maxLines = 1, colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ), modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)
                )
                .padding(2.dp)

        )
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Correo electrónico", color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = correo, onValueChange = { correo = it }, placeholder = {
                Text(
                    text = "Introduce tu correo electrónico",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }, singleLine = true, maxLines = 1, colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ), modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)
                )
                .padding(2.dp)

        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Contraseña (Mín.6 carac.)", color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = contraseña,
            onValueChange = { contraseña = it },
            placeholder = {
                Text(
                    text = "Introduce tu contraseña",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)
                )
                .padding(2.dp)
        )
        Spacer(modifier = Modifier.height(42.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(2.dp, Blue50),
            onClick = {
                // Llamamos a la funcion para crear un nuevo usuario
                usuarioActual.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Registrado con éxito", Toast.LENGTH_SHORT)
                                .show()
                            guardarUsuario(nombreUsuario, correo)
                            context.startActivity(Intent(context, BusScreen::class.java))
                        } else {
                            Toast.makeText(
                                context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue50),
            enabled = correo.isNotBlank() && contraseña.isNotBlank(),
        ) {
            Text(
                text = "Registrarse",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Ya tienes una cuenta?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(modifier = Modifier.clickable {
                context.startActivity(
                    Intent(
                        context, LogInScreen::class.java
                    )
                )
            }, text = "Inicia sesión", style = MaterialTheme.typography.bodySmall, color = Blue50)
        }
    }
}

private fun guardarUsuario(nombreUsuario: String, correo: String) {
    val usuarioNuevo = hashMapOf(
        "correoElectronico" to correo,
        "nombreUsuario" to nombreUsuario,
    )
    db.collection("Usuarios")
        .add(usuarioNuevo)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Alerta añadido con id: $documentReference.id")
        }.addOnFailureListener { e ->
            Log.w("Firestore", "No se ha podido guardar el alerta", e)
        }
}