package com.example.traq.components

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import coil.compose.AsyncImage
import com.example.traq.ui.theme.TraqTheme
import java.nio.file.WatchEvent

fun getCurrentUserInfo(): FirebaseUser? {
    val user = FirebaseAuth.getInstance().currentUser
    return user
}

@Composable
fun Header() {
    val user = FirebaseAuth.getInstance().currentUser
    val name = user?.displayName
    val email = user?.email
    val urlProfilePic = user?.photoUrl

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(
                MaterialTheme.colorScheme.secondary, RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp)
            )
            .padding(top = 42.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$name",
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = "$email", style = MaterialTheme.typography.bodySmall, color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        AsyncImage(
            model = urlProfilePic,
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(46.dp)
        )
    }

}

