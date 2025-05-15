package com.example.traq.dataClasses

import com.google.android.gms.maps.model.LatLng

data class BusStopData(
    val name: String,
    val lat: Double,
    val long: Double,
    val hour: String
)




val returnParada1 = BusStopData("Paco", 45.00203, -32.3323, "14:00")
val returnParada2 = BusStopData("Juan", 45.00203, -32.3323, "14:00")

val idaParadas = listOf(
    LatLng(40.000,-3.82)
)
val vueltaParadas = listOf(returnParada1, returnParada2)
