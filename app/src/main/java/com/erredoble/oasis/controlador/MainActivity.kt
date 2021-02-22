package com.erredoble.oasis.controlador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erredoble.oasis.R

class MainActivity : AppCompatActivity() {

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        // En cuanto cargue esperar un poco y quitar el Splash Screen.
        Thread.sleep(500)
        setTheme(R.style.AppTheme)

        // Propio del "onCreate", le pasa el estado de la actividad y vincula la clase con el layout.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()
    }
}