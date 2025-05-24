import retrofit2.http.GET

interface ApiService{
    // Le pasamos la url de la API
    @GET("https://magicloops.dev/api/loop/4682597e-e90d-497a-8ead-fd30cb7d9cf5/run")
    // Llamamos a la funcion para obtener los billetes
    suspend fun obtenerBillete(): List<TrainTicket>
}