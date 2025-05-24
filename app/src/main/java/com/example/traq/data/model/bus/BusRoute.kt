data class BusRoute(
    val nombre: String,
    val ciudad: String,
    val paradas: List<BusStop>,
)

val ruta1 = BusRoute(
    nombre = "Urbano",
    ciudad = "Seseña",
    paradas = paradas1,
)

val ruta2 = BusRoute(
    nombre = "Interbus",
    ciudad = "Madrid/Seseña",
    paradas = paradas2,
)

val rutas = listOf<BusRoute>(ruta1, ruta2)