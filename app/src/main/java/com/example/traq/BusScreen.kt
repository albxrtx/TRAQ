package com.example.traq

import BusRoute
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.traq.components.Header
import com.example.traq.components.Navbar
import com.example.traq.ui.theme.TraqTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

import routes
import route1

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts


class BusScreen : AppCompatActivity() {

    // Declaramos una variable de tipo FusedLocationProviderClient para obtener la ubicacion
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Declaramos variables para guardar la ubicaión
    private var userLat: Double? = null
    private var userLong: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inizializamos la variable fusedLocationClient con la Activity actual
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            TraqTheme {
                BusScreenContent()
            }
        }
        // Obtenemos la ubicacion del usuario
        getUserLocation()

    }

    // Funcion para obtener la ubicacion del usuario
    private fun getUserLocation() {
        // Comprobamos si los permisos necesarios para obtener la ubicacion han sido aceptados
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitamos permisos para obtener la ubicación
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }
        // Una vez que el usuario haya aceptado los permisos obtenemos su ubicacion
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                userLat = location.latitude
                userLong = location.longitude
            }
        }
    }

    // Funcion/pantalla para aceptar los permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Si acepta los permisos llamamaos a la funcion para obtener su ubicacion
        if (isGranted) {
            getUserLocation()
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
            val scrollState = rememberScrollState()
            Header()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                BusCard()
            }
            Navbar()
        }
    }

    @Composable
    private fun BusCard() {
        routes.forEach { route ->

            var expanded by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp)
                    .animateContentSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.secondary, if (expanded) RoundedCornerShape(
                                12.dp, 12.dp, 0.dp, 0.dp
                            ) else RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
                        )
                        .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(com.example.traq.R.drawable.bus),
                        contentDescription = "Bus icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${route.name} - ${route.city}",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (expanded) {
                    Column(
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.tertiary,
                            RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.tertiary
                                )
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Ubicacion actual: $userLat + $userLong",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        displayGoogleMap(route)
                    }
                }
            }
        }

    }

    @Composable
    private fun displayGoogleMap(route: BusRoute) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(40.10451921563171, -3.6939612498723546), 16f
            )
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(10.dp)), cameraPositionState = cameraPositionState
        ) {
            route.stops.forEach { stop ->
                Circle(
                    center = LatLng(stop.latitude, stop.longitude),
                    radius = 15.0,
                    strokeColor = Color.Blue,
                    strokeWidth = 2f,
                    fillColor = Color.Blue
                )
            }

            Polyline(
                points = route.stops.map {
                    LatLng(
                        it.latitude, it.longitude
                    )
                }, color = Color.Blue, width = 10f
            )

        }
    }
}


