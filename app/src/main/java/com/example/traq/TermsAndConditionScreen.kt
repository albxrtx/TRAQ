package com.example.traq

import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text

class TermsAndConditionScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                Text("Aquí van los términos y condiciones")
            }
    }


}