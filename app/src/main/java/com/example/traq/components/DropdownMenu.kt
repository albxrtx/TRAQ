import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun DropwdownMenu(opciones: List<String>, painter: Painter, onSeleccionChange: (String) -> Unit) {
    var expandido by remember { mutableStateOf(false) }
    var seleccion by remember { mutableStateOf("Escoge una opcion") }

    Column {
        TextButton(onClick = { expandido = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = seleccion,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(com.traq.app.R.drawable.dropdown),
                    contentDescription = "Dropdown icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        DropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
            expanded = expandido,
            onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painter,
                                contentDescription = "Bus icon",
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(opcion, color = MaterialTheme.colorScheme.onBackground)
                        }
                    },
                    onClick = {
                        seleccion = opcion
                        onSeleccionChange(opcion)
                        expandido = false
                    })
            }
        }
    }
}