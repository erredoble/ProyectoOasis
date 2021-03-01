package com.erredoble.oasis.controlador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.erredoble.oasis.R
import com.erredoble.oasis.modelo.dao.BDFuentes
import com.erredoble.oasis.modelo.entidad.Area
import kotlinx.android.synthetic.main.activity_busqueda_area.*

/**
 * Actividad de busqueda por area.
 * Segun el area seleccionada en el spinner se mostrara una lista de las fuentes existentes.
 */
class BusquedaAreaActivity : AppCompatActivity() {
    // ########################### CAMPOS ###########################
    private lateinit var BD: BDFuentes
    private lateinit var coleccionAreas: List<Area>
    private lateinit var adaptador: ArrayAdapter<String>

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        // Propio del "onCreate", le pasa el estado de la actividad y vincula la clase con el layout.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busqueda_area)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Obtener una instancia del controlador de la BD.
        BD = BDFuentes.getInstancia(this)

        // Rellenar el spinner con los nombres de las areas.
        cargarAreasSpinner()

        //Llamar a los eventos de los botones
        eventosBoton()
    }

    // ########################### METODOS ###########################
    /** Lee las areas de la BD y las carga en el Spinner. */
    private fun cargarAreasSpinner() {
        try {
            // Obtener las areas de la BD.
            coleccionAreas = BD.areaDao().getAreas()

            // Crear y rellenar el adaptador del Spinner con las areas de la BD.
            adaptador = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item)
            for (area in coleccionAreas) {
                adaptador.add(area.nombre_area)
            }

            // Asignar el adaptador al Spinner.
            spnArea.adapter = adaptador
        } catch (ex: Exception) {
            Log.e("Error", ex.message!!)
            Toast.makeText(this, "Error + ${ex.message}", Toast.LENGTH_LONG).show()

        }
    }
    // ########################### EVENTOS DE BOTON ###########################
    private fun eventosBoton() {
        btn_ir.setOnClickListener { accionIr() }
        btn_volver.setOnClickListener { finish() }
    }

    /** Lo que sucede al pulsar el boton ir */
    private fun accionIr() {
        val areaSeleccionada = coleccionAreas[spnArea.selectedItemPosition]
        mostrarActividadListado(areaSeleccionada.id_area)
    }

    /** cargar la actividad ListadoArbolesActivity y pasarle el idArea*/
    private fun mostrarActividadListado(idArea: Int){
        val intent : Intent = Intent(this,ListadoFuentesActivity::class.java)
            .putExtra("idArea", idArea)
        startActivity(intent)
    }

}