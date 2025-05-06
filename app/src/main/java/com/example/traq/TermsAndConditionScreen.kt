package com.example.traq

import android.R
import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.traq.ui.theme.TraqTheme

class TermsAndConditionScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraqTheme {
                TermsAndCondition()
            }
        }
    }

    @Composable
    private fun TermsAndCondition() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(scrollState),
//            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Términos y condiciones de Uso",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = "1. Introducción",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Traq se trata de una aplicación móvil desarrollada para mantenerte informado en tiempo real sobre las líneas de transporte público de España " +
                            "\nAl acceder o utilizar nuestra aplicación, aceptas a quedar vinculado bajo estos Términos y Condiciones" +
                            "\nSi no estás de acuerdo con algunos de ellos, le rogamos que no utilize nuestra aplicación" +
                            "\nEl uso de Traq implica la aceptación plena y sin reservas de todos los términos incluidos en este documento.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(
                    text = "2. Uso de la app",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Traq ofrece posibilidades como: " +
                            "\n· Consultar la ruta y paradas de transporte público en España." +
                            "\n· Enviar y leer comentarios sobre el estado de la línea." +
                            "\nEl usuario se compromete a:" +
                            "\nUtilizar la aplicación de manera responsable, ética y conforme a la legalidad vigente." +
                            "\nNo enviar comentarios falsos, ofensivos o que inciten al odio, la violencia o el pánico." +
                            "\nNo manipular la información mostrada ni interferir en el funcionamiento del sistema." +
                            "\nLa utilización indebida de Traq podrá derivar en la suspensión temporal o definitiva del acceso a la aplicación.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(
                    text = "3. Registro e Inicio de Sesión",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Para acceder a la aplicación es necesario registrarse previamente con una cuenta de Google." +
                            " Este inicio de sesión permite una experiencia personalizada y segura." +
                            "\nEl usuario es responsable de mantener la confidencialidad de sus credenciales de acceso y de todas las actividades que ocurran bajo su cuenta." +
                            " Si detectas un uso no autorizado, deberás notificarlo de inmediato.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(
                    text = "4. Derechos de Propiedad Intelectual",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Todos los derechos de propiedad intelectual sobre el diseño, interfaz, funcionalidades, código fuente y contenidos originales de Traq pertenecen exclusivamente a sus desarrolladores." +
                            "\n Está totalmente prohibido: " +
                            "\n· Copiar, reproducir, distribuir o modificar cualquier parte de la app sin autorización." +
                            "\n· Usar elementos de la app con fines comerciales sin consentimiento previo por escrito.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(
                    text = "5. Política de Privacidad",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Traq respeta tu privacidad. Recopilamos y tratamos datos mínimos necesarios para el correcto funcionamiento de la app, como el proveedor de acceso (Google) y los comentarios que decides enviar.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(
                    text = "6. Limitación de Responsabilidad",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Traq respeta tu privacidad. Recopilamos y tratamos datos mínimos necesarios para el correcto funcionamiento de la app, como el proveedor de acceso (Google) y los comentarios que decides enviar.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

}