import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

data class Users(
    val correoElectronico: String, val nombreUsuario: String
)

fun obtenerNombreUsuarioPorCorreo(
    correo: String,
    callback: (String) -> Unit
) {
    // Creamos una instancia de Firestore
    val db = FirebaseFirestore.getInstance()

    db.collection("Usuarios")
        // Buscamos por correo similares
        .whereEqualTo("correoElectronico", correo).get()
        .addOnSuccessListener { result ->
            // Si se encuentra 2 correos iguales devolvemos el nombre
            val nombre = result.documents.firstOrNull()?.getString("nombreUsuario") ?: ""
            callback(nombre)
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error al obtener nombre de usuario", e)
            callback("")
        }
}

