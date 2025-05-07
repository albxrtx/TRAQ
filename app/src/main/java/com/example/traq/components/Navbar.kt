package com.example.traq.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Navbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary,
                RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.bus),
                contentDescription = "Bus icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Autobús", color = Color.White
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.subway),
                contentDescription = "Subway icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Metro", color = Color.White
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.train),
                contentDescription = "Train icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Trenes", color = Color.White
            )
        }
    }
}