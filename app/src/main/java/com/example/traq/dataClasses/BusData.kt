package com.example.traq.dataClasses


class BusData(
    val name: String,
    val city: String,
    val busStops: List<BusStopData>,
    val returnBusStops: List<BusStopData>
)


