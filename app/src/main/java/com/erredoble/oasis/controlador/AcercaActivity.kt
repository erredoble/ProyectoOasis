package com.erredoble.oasis.controlador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erredoble.oasis.R
import kotlinx.android.synthetic.main.activity_busqueda_area.*


/**
 * Actividad que muestra la informacion sobre la APP y sus desarrollador..
 */
class AcercaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Evento del boton volver.
        btn_volver.setOnClickListener { finish() }
    }
}