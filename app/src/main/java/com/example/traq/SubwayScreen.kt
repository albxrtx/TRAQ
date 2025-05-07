package com.example.traq

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.traq.components.Header
import com.example.traq.components.Navbar
import com.example.traq.ui.theme.TraqTheme

class SubwayScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraqTheme {
                SubwayScreenContent()
            }
        }
    }

    @Composable
    private fun SubwayScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header()
            Text(
                text = "Esta es la pestaña del metro"
            )
            Navbar()
        }
    }
}