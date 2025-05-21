package com.example.traq

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.traq.ui.theme.Blue50
import com.example.traq.ui.theme.TraqTheme
import com.google.firebase.auth.FirebaseAuth

class HomeScreen : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            TraqTheme {
                val usuarioActual = FirebaseAuth.getInstance().currentUser
                if (usuarioActual != null) {
                    val intent = Intent(this@HomeScreen, BusScreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    HomeScreenContent()
                }
            }
        }
    }

    @Composable
    fun HomeScreenContent() {
        val context = LocalContext.current
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 46.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    modifier = Modifier.size(400.dp),
                    painter = painterResource(R.drawable.log_in_image),
                    contentDescription = "LogIn image"
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Accede y sigue tu ruta.",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, Blue50),
                    onClick = {
                        context.startActivity(
                            Intent(
                                context, LogInScreen::class.java
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue50)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, Blue50),
                    onClick = {
                        context.startActivity(
                            Intent(
                                context, RegisterScreen::class.java
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = "Registrarse",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Blue50,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Row(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "Al registrarte, aceptas nuestros ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "términos y condiciones",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            val intent =
                                Intent(this@HomeScreen, TermsAndConditionScreen::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
