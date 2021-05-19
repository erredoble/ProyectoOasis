package com.erredoble.oasis.controlador

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erredoble.oasis.R
import kotlinx.android.synthetic.main.activity_busqueda_area.*


/**
 * Actividad que muestra la informacion sobre la APP y su desarrollador..
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