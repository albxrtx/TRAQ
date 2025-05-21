package com.example.traq.components

import android.R
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import coil.compose.AsyncImage
import com.example.traq.BusScreen
import com.example.traq.LogInScreen
import com.example.traq.TrainScreen
import com.example.traq.ui.theme.TraqTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.nio.file.WatchEvent
import kotlin.Unit.toString

@Composable
fun Header() {
    var expandido by remember { mutableStateOf(false) }
    val usuario = FirebaseAuth.getInstance().currentUser
    val nombre = usuario?.displayName ?: "Usuario"
    val email = usuario?.email ?: "Sin correo"
    val urlFotoPerfil = usuario?.photoUrl

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(
                MaterialTheme.colorScheme.secondary, RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp)
            )
            .padding(horizontal = 20.dp)
            .animateContentSize()
    ) {
        Spacer(modifier = Modifier.height(42.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = nombre,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            AsyncImage(
                model = urlFotoPerfil,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(46.dp)
                    .clickable { expandido = !expandido })
        }

        if (expandido) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Button(
                    border = BorderStroke(2.dp, Color.White),
                    onClick = { cerrarSesion(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Cerrar sesión",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = com.example.traq.R.drawable.logout),
                            contentDescription = "LogOut icon",
                            tint = Color.Black,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun cerrarSesion(context: Context) {
    // Obtenemos los datos del usuario
    val usuario = FirebaseAuth.getInstance().currentUser
    // Mostramos un Toast avisando que hemos cerrado la sesión
    Toast.makeText(context, "Cerrando sesión de ${usuario?.displayName}", Toast.LENGTH_LONG).show()

    // Cierra la sesión en Firebase
    FirebaseAuth.getInstance().signOut()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        // Le pasamos en Web Client Id para que Google compruebe desde que app se realiza la acción
        .requestIdToken(com.example.traq.R.string.default_web_client_id.toString())
        // Le pasamos el correo del usuario
        .requestEmail().build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    // Cerramos la sesión del usuario
    googleSignInClient.signOut()

    // Mandamos al usuario a la pestaña de Inicio para volver a iniciar sesión
    context.startActivity(
        Intent(
            context, LogInScreen::class.java
        )
    )

}