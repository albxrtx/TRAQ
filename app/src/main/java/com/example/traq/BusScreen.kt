package com.example.traq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.traq.components.Header
import com.example.traq.components.Navbar
import com.example.traq.ui.theme.TraqTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

class BusScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraqTheme {
                BusScreenContent()
            }
        }

    }

    @Composable
    private fun BusScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Header()
            Text(
                text = "Esta es la pestaña de los autobuses"
            )
//            BusCard()
            Navbar()
        }
    }
}
//    @Composable
//    private fun BusCard() {
//        val cameraPositionState = rememberCameraPositionState {
//            position =
//                CameraPosition.fromLatLngZoom(LatLng(40.10451921563171, -3.6939612498723546), 16f)
//        }
//        val linea = linea1
//        val paradas = linea.busStops
//
//        val ida = mutableListOf<LatLng>()
//
//        for (busStop in paradas) {
//            val latLng = LatLng(busStop.lat, busStop.long)
//            ida.add(latLng)
//        }
//        val vuelta = listOf(
//            LatLng(40.10924004456302, -3.659479121947875),
//            LatLng(40.109342750177376, -3.678337262321936),
//            LatLng(40.104300648052906, -3.6939522567234158),
//            LatLng(40.10318898210247, -3.697387293198008),
//            LatLng(40.10109207231496, -3.698670915164194),
//        )
//
//        var expanded by remember { mutableStateOf(false) }
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { expanded = !expanded }
//                .padding(16.dp)) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        MaterialTheme.colorScheme.tertiary,
//                        RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
//                    )
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Icon(
//                    painter = painterResource(com.example.traq.R.drawable.bus),
//                    contentDescription = "Bus icon",
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//                Text(
//                    text = "${linea.name} - ${linea.city}"
//                )
//            }
//            Row {
//                val parada = linea.busStops[1]
//                Text(
//                    text = "Parada actual: \n${parada.name}"
//                )
//                Text(
//                    text = "Hora aprox. llegada: \n${parada.hour}"
//                )
//            }
//            if (expanded) {
//                Column {
//                    GoogleMap(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(250.dp),
//                        cameraPositionState = cameraPositionState
//                    ) {
//                        Marker(
//                            state = MarkerState(ida[0]), title = "Parada actual"
//                        )
//                        Polyline(
//                            points = ida, color = Color.Blue, width = 10f
//                        )
//                        Polyline(
//                            points = vuelta, color = Color.Red, width = 10f
//                        )
//
//                    }
//                }
//            }
//        }
//
//
//    }
//}
