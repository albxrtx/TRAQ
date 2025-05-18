data class BusRoute(
    val name: String,
    val city: String,
    val stops: List<BusStop>,
)

val route1 = BusRoute(
    name = "Urbano",
    city = "Seseña",
    stops = stops1,
)

val route2 = BusRoute(
    name = "Interbus",
    city = "Madrid/Seseña",
    stops = stops2,
)

val routes = listOf<BusRoute>(route1, route2)