package com.erredoble.oasis.controlador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.erredoble.oasis.R
import com.erredoble.oasis.modelo.dao.BDFuentes
import com.erredoble.oasis.modelo.entidad.Fuente
import kotlinx.android.synthetic.main.activity_info_fuente.*
import kotlinx.android.synthetic.main.activity_listado_fuentes.*
import kotlinx.android.synthetic.main.activity_listado_fuentes.btn_volver

class InfoFuenteActivity : AppCompatActivity() {

    // ########################### CAMPOS ###########################
    private lateinit var BD: BDFuentes
    private lateinit var fuente: Fuente
    private var idFuente: Int = 0
    private var idArea: Int = 0

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_fuente)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Obtener el controlador de la base de datos.
        BD = BDFuentes.getInstancia(this)

        // Obtener los id de Area y Arbol de la actividad anterior.
        getIdFuenteAreaActividadAnterior()

        // Cargar el arbol seleccionado.
        cargarFuente()

        //Evento de boton volver
        btn_volver.setOnClickListener { finish() }

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
            // Obtener el árbol seleccionado.
            fuente = BD.fuenteDao().getFuente(idFuente)

            //Poner datos en las etiquetas
            lblDescripcion.text = fuente.descrip_fuente

            // Hacer desaparecer los campos vacios.
            mostrarOcultarCampos()
        }
    }

    private fun mostrarOcultarCampos() {
        if (fuente.descrip_fuente == null) {
            tituloDescripcion.visibility = View.GONE
            lblDescripcion.visibility = View.GONE
        }
    }

}
