package com.erredoble.oasis.controlador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.erredoble.oasis.R
import com.erredoble.oasis.modelo.dao.BDFuentes
import com.erredoble.oasis.modelo.entidad.Fuente
import kotlinx.android.synthetic.main.activity_listado_fuentes.*

/**
 * Carga una lista con todas las fuentes del area seleccionada.
 * */

class ListadoFuentesActivity : AppCompatActivity() {
    // ########################### CAMPOS ###########################
    private var idArea: Int = 0
    private lateinit var BD: BDFuentes
    private lateinit var coleccionFuentes: List<Fuente>
    private lateinit var adaptador: ArrayAdapter<String>

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_fuentes)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Ahora obtenemos el idArea de la actividad BusquedaAreaActivity.
        getIdAreaDeActividadAnterior()

        // Obtener el controlador de la base de datos.
        BD = BDFuentes.getInstancia(this)

        // Poner el título a la actividad.
        mostrarTitulo()

        // Cargar y mostrar el listado de fuentes de el area seleccionada.
        mostrarListadoFuentes()

        // Eventos de boton.
        eventosBoton()
    }

    // ########################### METODOS ###########################
    private fun eventosBoton() {
        btn_volver.setOnClickListener { finish() }
        lstListadoFuentes.setOnItemClickListener { parent, view, position, id ->
            val idFuenteSeleccionada = coleccionFuentes[position].id_fuente
            cargarActividadInfoFuente(idFuenteSeleccionada, idArea)
        }
    }

    private fun mostrarTitulo() {
        var cadenaTitulo =
            "Fuentes de" + " " + BD.areaDao().getArea(idArea).nombre_area
        lblTitulo.text = cadenaTitulo
    }

    private fun getIdAreaDeActividadAnterior() {
        var idAreaRecibido = intent.extras?.getInt("idArea")
        if (idAreaRecibido != null) {
            idArea = idAreaRecibido
        } else {
            Log.e(
                "Error al recibir idArea", "No se ha podido recibir el id de area de la " +
                        "actividad BusquedaAreaActivity"
            )
        }
        Toast.makeText(this, idArea.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun mostrarListadoFuentes() {
        // Cargar listado de fuentes de el area seleccionada.
        coleccionFuentes = BD.fuenteDao().getFuentes(idArea)

        //Crear el adaptador
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1)

        // Cargar el adaptador con todas las fuentes almacenadas en List.
        for (fuente in coleccionFuentes) {
            adaptador.add(fuente.descripcion)
        }
        // Asignar el adaptador al ListView.
        lstListadoFuentes.adapter = adaptador
    }

    private fun cargarActividadInfoFuente(idFuente: Int, idArea: Int) {
        val intent = Intent(this, InfoFuenteActivity::class.java).putExtra("idFuente", idFuente)
            .putExtra("idArea", idArea)
        startActivity(intent)
    }
}
