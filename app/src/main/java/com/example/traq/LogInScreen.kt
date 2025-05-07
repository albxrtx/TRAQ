package com.example.traq

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.example.traq.ui.theme.TraqTheme

class LogInScreen : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(this)

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id)) // Este es tu client ID
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        val currentUser = FirebaseAuth.getInstance().currentUser


        setContent {
            TraqTheme {
                if (currentUser != null) {
                    val intent = Intent(this@LogInScreen, BusScreen::class.java)
                    startActivity(intent)
                    finish()
                } else
                    LoginScreen(
                        onSignInClicked = { launchSignInFlow() }
                    )
            }
        }
    }

    @Composable
    fun LoginScreen(onSignInClicked: () -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    Toast.makeText(
                                        this@LogInScreen,
                                        "Bienvenido, ${user?.displayName}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // 👉 Lanzar nueva Activity
                                    val intent = Intent(this@LogInScreen, BusScreen::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LogInScreen,
                                        "Error en autenticación",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Bienvenido a Traq",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Consulta el estado de las líneas de transporte público en tiempo real. Comenta y comparte tus experiencias con la comunidad.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Image(
                    modifier = Modifier
                        .size(400.dp)
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    painter = painterResource(R.drawable.undraw),
                    contentDescription = "Logo de Traq"
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                    onClick = { onSignInClicked() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_google),
                            contentDescription = "Logo de Google"
                        )
                        Text(
                            text = "Iniciar sesión con Google",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Row(modifier = Modifier.padding(top = 12.dp)) {
                    Text(
                        text = "Al iniciar sesión, aceptas nuestros ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    val context = LocalContext.current
                    Text(
                        text = "términos y condiciones",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        TermsAndConditionScreen::class.java
                                    )
                                )
                            }
                    )
                }

            }


        }
        LaunchedEffect(Unit) {
            signInFlowLauncher = launcher
        }
    }

    private var signInFlowLauncher by mutableStateOf<androidx.activity.result.ActivityResultLauncher<IntentSenderRequest>?>(
        null
    )

    private fun launchSignInFlow() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    signInFlowLauncher?.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(this, "No se pudo iniciar el inicio de sesión", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}

