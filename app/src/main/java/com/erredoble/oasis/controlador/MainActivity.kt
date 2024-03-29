package com.erredoble.oasis.controlador

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.erredoble.oasis.R
import com.erredoble.oasis.modelo.dao.BDFuentes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var bdFuentes: BDFuentes

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

        // Obtener una instancia de la base de datos con room.
        bdFuentes = BDFuentes.getInstancia(this)

        // Cargar los eventos de boton.
        eventosBoton()
    }


    // ########################### METODOS ###########################
    /** Controla lo que sucedera al pulsar un boton de esta actividad. */
    private fun eventosBoton() {
        btn_mapa.setOnClickListener { cargarActividad(MapsActivity::class.java) }
        btn_busqueda.setOnClickListener { cargarActividad(BusquedaAreaActivity::class.java) }
        btn_acerca.setOnClickListener { cargarActividad(AcercaActivity::class.java) }
        btn_salir.setOnClickListener { finish() }
    }

    // ########################### METODOS AUXILIARES ###########################
    private fun cargarActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}