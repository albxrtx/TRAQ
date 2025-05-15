package com.example.traq.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.traq.BusScreen
import com.example.traq.SubwayScreen
import com.example.traq.TermsAndConditionScreen
import com.example.traq.TrainScreen
import android.app.Activity

@Composable
fun Navbar() {
    val context = LocalContext.current
    val activity = context as? Activity
    val screenName = activity?.javaClass?.simpleName ?: "Unknown"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary, RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(20.dp), horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.clickable {
                context.startActivity(
                    Intent(
                        context, BusScreen::class.java
                    )
                )
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.bus),
                contentDescription = "Bus icon",
                tint = if (screenName == "BusScreen") Color.White else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Autobús",
                color = if (screenName == "BusScreen") Color.White else Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier.clickable {
                context.startActivity(
                    Intent(
                        context, SubwayScreen::class.java
                    )
                )
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.subway),
                contentDescription = "Subway icon",
                tint = if (screenName == "SubwayScreen") Color.White else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Metro",
                color = if (screenName == "SubwayScreen") Color.White else Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier.clickable {
                context.startActivity(
                    Intent(
                        context, TrainScreen::class.java
                    )
                )
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(com.example.traq.R.drawable.train),
                contentDescription = "Train icon",
                tint = if (screenName == "TrainScreen") Color.White else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Tren",
                color = if (screenName == "TrainScreen") Color.White else Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


