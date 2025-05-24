package com.example.traq

import BusRoute
import BusStop
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
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

import rutas

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import com.example.traq.ui.theme.Blue60
import com.example.traq.ui.theme.Blue70


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
        obtenerUbicacion()

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
                rutas.forEach { ruta ->
                    BusCard(ruta)
                }
            }
            Navbar()
        }
    }

    @Composable
    private fun BusCard(route: BusRoute) {

        var expandido by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) { expandido = !expandido }
                .padding(16.dp)
                .animateContentSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Blue70, if (expandido) RoundedCornerShape(
                            12.dp, 12.dp, 0.dp, 0.dp
                        ) else RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
                    )
                    .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(com.example.traq.R.drawable.bus),
                    contentDescription = "Bus icon",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${route.nombre} - ${route.ciudad}",
                    color = Color.White
                )
            }

            if (expandido) {
                Column(
                    modifier = Modifier
                        .background(
                            Blue60, RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
                        )
                        .padding(16.dp)
                ) {
                    // Creamos una variable con la parada mas cercana si el usuario ha concedido su ubicacion
                    val paradaCercana = if (userLat != null && userLong != null) {
                        encontrarParaMasCercana(userLat!!, userLong!!, route.paradas)
                    } else {
                        null
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        // Mostramos el nombre de la parada mas cercana o un mensaje predeterminado
                        text =
                            "Parada cercana: ${paradaCercana?.nombre ?: "Ubicacion desconocida"}",
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Llamamos a la función para mostrar el mapa
                    MostrarMapaGoogle(route, paradaCercana, userLat, userLong)
                }
            }
        }

    }

    @Composable
    private fun MostrarMapaGoogle(
        route: BusRoute, paradaCercana: BusStop?, userLat: Double?, userLong: Double?
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = if (userLat != null && userLong != null) {
                CameraPosition.fromLatLngZoom(
                    LatLng(paradaCercana?.latitud ?: 40.00, paradaCercana?.longitud ?: 40.00), 16f
                )
            } else {
                CameraPosition.fromLatLngZoom(
                    LatLng(40.10451921563171, -3.6939612498723546), 16f
                )
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)), cameraPositionState = cameraPositionState
        ) {
            // Recorremos cada parada y creamos un marca en ella
            route.paradas.forEach { parada ->
                Circle(
                    center = LatLng(parada.latitud, parada.longitud),
                    radius = 15.0,
                    strokeColor = Color.Red,
                    strokeWidth = 2f,
                    fillColor = Color.Red
                )
            }
            if (userLat != null && userLong != null) {
                Circle(
                    center = LatLng(userLat, userLong),
                    radius = 20.0,
                    strokeColor = Color(33, 150, 243, 166),
                    strokeWidth = 2f,
                    fillColor = Color(33, 150, 243, 166)
                )
                Circle(
                    center = LatLng(userLat, userLong),
                    radius = 10.0,
                    strokeColor = Color.Blue,
                    strokeWidth = 2f,
                    fillColor = Color.Blue
                )
            }
            // Creamos un Marker con la ubicacion y nombre de la parada maas cercana
            if (paradaCercana != null) {
                Marker(
                    title = "Parada mas cercana",snippet = "${paradaCercana.nombre}", state = MarkerState (
                        position = LatLng(
                            paradaCercana.latitud, paradaCercana.longitud
                        )
                    )

                )
            }
            // Creamos lineas para conectar las paradas de cada ruta
            Polyline(
                points = route.paradas.map {
                    LatLng(
                        it.latitud, it.longitud
                    )
                }, color = Color.Red, width = 10f
            )

        }
    }

    // Funcion para obtener la ubicacion del usuario
    private fun obtenerUbicacion() {
        // Comprobamos si los permisos necesarios para obtener la ubicacion han sido aceptados
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitamos permisos para obtener la ubicación
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            // Salimos de la funcion si el usuario no aacepta los permisos
            return
        }
        // Una vez que el usuario haya aceptado los permisos obtenemos su ubicacion
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Comprobamos que haya una ubicacion
            if (location != null) {
                userLat = location.latitude
                userLong = location.longitude
            }
        }.addOnFailureListener { exception ->
            Log.e("Ubicacion", "Hubo problemas al obtener la ubicación", exception)
        }
    }

    // Funcion/pantalla para aceptar los permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Si acepta los permisos llamamaos a la funcion para obtener su ubicacion
        if (isGranted) {
            obtenerUbicacion()
        }
    }

    // funcion para encontrar la parada mas cercana
    fun encontrarParaMasCercana(userLat: Double, userLong: Double, stops: List<BusStop>): BusStop? {
        // Inicializamos la parada cercana a null
        var paradaCercana: BusStop? = null
        // Le damos el maximo valor que pueda contener un float
        var distanciaMasCorta = Float.MAX_VALUE

        // Creamos un objeto de tipo Location con los datos del usuario
        val ubicacionUsuario = Location("user").apply {
            latitude = userLat
            longitude = userLong
        }
        // Recorremos las paradas que hemos pasado
        for (stop in stops) {
            // Por cada parada creamos un objeto de tipo Location
            val ubicacionParada = Location("stop").apply {
                latitude = stop.latitud
                longitude = stop.longitud
            }
            // Calculamos la distancia entre las dos ubicaciones
            val distanciaEntreUbicaciones = ubicacionUsuario.distanceTo(ubicacionParada)
            // Comprobamos la distancia mas corta entre dos ubicaciones
            if (distanciaEntreUbicaciones < distanciaMasCorta) {
                // Actualizamos la parada mas cercana con la actual
                distanciaMasCorta = distanciaEntreUbicaciones
                paradaCercana = stop
            }

        }
        return paradaCercana
    }

}

