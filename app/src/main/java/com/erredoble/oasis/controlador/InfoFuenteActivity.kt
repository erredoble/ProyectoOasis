package com.erredoble.oasis.controlador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.erredoble.oasis.R
import com.erredoble.oasis.modelo.dao.BDFuentes
import com.erredoble.oasis.modelo.entidad.Fuente
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_info_fuente.*
import kotlinx.android.synthetic.main.activity_listado_fuentes.btn_volver

class InfoFuenteActivity : AppCompatActivity() {

    // ########################### CAMPOS ###########################
    private lateinit var BD: BDFuentes
    private lateinit var fuente: Fuente
    private var idFuente: Int = 0
    private var idArea: Int = 0
    private lateinit var coordenadas: String
    private lateinit var mMap: GoogleMap

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_fuente)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Obtener el controlador de la base de datos.
        BD = BDFuentes.getInstancia(this)

        // Obtener los id de Area y Fuente de la actividad anterior.
        getIdFuenteAreaActividadAnterior()

        // Cargar la fuente seleccionada.
        cargarFuente()

        //Ir a la fuente seleccionada en maps
//        irAFuenteEnMaps()

        //Evento de boton volver
        btn_volver.setOnClickListener { finish() }
        btn_Ir.setOnClickListener { irAFuente() }
    }

    // ########################### METODOS ###########################
    private fun getIdFuenteAreaActividadAnterior() {
        val idFuenteRecibida = intent.extras?.getInt("idFuente")
        if (idFuenteRecibida != null) {
            idFuente = idFuenteRecibida
        } else {
            idFuente = -1
        }

        val idAreaRecibida = intent.extras?.getInt("idArea")
        if (idAreaRecibida != null) {
            idArea = idAreaRecibida
        } else {
            idArea = -1
        }
    }

    private fun cargarFuente() {
        if (idFuente != -1) {
            // Obtener la fuente seleccionada.
            fuente = BD.fuenteDao().getFuente(idFuente)

            //Poner datos en las etiquetas
            lblDescripcion.text = fuente.descripcion

            // Hacer desaparecer los campos vacios.
            mostrarOcultarCampos()
        }
    }

    private fun mostrarOcultarCampos() {
        if (fuente.descripcion == null) {
            tituloDescripcion.visibility = View.GONE
            lblDescripcion.visibility = View.GONE
        }
    }

    private fun irAFuente() {
        val intent: Intent = Intent(this, MapsActivity::class.java)
            .putExtra("idFuente", idFuente)
        startActivity(intent)
    }

}
