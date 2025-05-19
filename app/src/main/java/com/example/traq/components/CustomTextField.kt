import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun CustomTextField(onMensajeChange: (String) -> Unit) {
    var mensaje by remember { mutableStateOf("") }

    TextField(
        value = mensaje,
        onValueChange = {
            mensaje = it
            onMensajeChange(it)
        },
        placeholder = {
            Text(
                text = "Introduce tu mensaje aquí",
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.LightGray
        )
    )
}