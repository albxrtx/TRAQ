import retrofit2.http.GET

interface ApiService{
    @GET("https://magicloops.dev/api/loop/4682597e-e90d-497a-8ead-fd30cb7d9cf5/run")
    suspend fun obtenerBillete(): List<TrainTicket>
}