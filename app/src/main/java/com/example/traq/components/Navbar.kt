package com.example.traq.components

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
import androidx.compose.ui.unit.dp
import com.example.traq.BusScreen
import com.example.traq.SubwayScreen
import com.example.traq.TrainScreen
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import com.example.traq.AlertsScreen
import com.example.traq.R

@Composable
fun Navbar() {
    val context = LocalContext.current
    val activity = context as? Activity
    val actualScreenName = activity?.javaClass?.simpleName ?: "Unknown"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary, RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(20.dp), horizontalArrangement = Arrangement.SpaceAround
    ) {
        toActivity(
            painter = painterResource(id = R.drawable.bus),
            screenClass = BusScreen::class.java,
            actualScreenName = actualScreenName,
            name = "Autobús",
            context = context
        )
        toActivity(
            painter = painterResource(id = R.drawable.train),
            screenClass = TrainScreen::class.java,
            actualScreenName = actualScreenName,
            name = "Tren",
            context = context
        )
        toActivity(
            painter = painterResource(id = R.drawable.subway),
            screenClass = SubwayScreen::class.java,
            actualScreenName = actualScreenName,
            name = "Metro",
            context = context
        )
        toActivity(
            painter = painterResource(id = R.drawable.alert),
            screenClass = AlertsScreen::class.java,
            actualScreenName = actualScreenName,
            name = "Alertas",
            context = context
        )
    }
}


@Composable
private fun toActivity(
    painter: Painter,
    screenClass: Class<*>,
    actualScreenName: String,
    name: String,
    context: Context
) {
    Column(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            context.startActivity(
                Intent(
                    context, screenClass
                )
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = "$name icon",
            tint = if (actualScreenName == screenClass.simpleName) MaterialTheme.colorScheme.onBackground else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = name,
            color = if (actualScreenName == screenClass.simpleName) MaterialTheme.colorScheme.onBackground else Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }

}

