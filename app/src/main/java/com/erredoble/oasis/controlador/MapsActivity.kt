package com.erredoble.oasis.controlador

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.erredoble.oasis.R
import com.erredoble.oasis.modelo.configuracion.Constantes
import com.erredoble.oasis.modelo.dao.BDFuentes
import com.erredoble.oasis.modelo.entidad.Fuente
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.dialog_accept.view.*

/**
 * Link Google Maps videotutorial: https://www.youtube.com/watch?v=3-84hvpb_zA
 * Obtener longitud y latitud (10:53): https://www.youtube.com/watch?v=ari3iD-3q8c
 *
 * TAREAS Y PROBLEMAS:
 * - No se ve el mapa.
 * - Si el GPS no esta activo y tiene permisos no funcionara, comprueba que el GPS esta encendido.
 * - Si no se conceden permisos que notifique con un mensaje y que salga de la actividad.
 */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    // ########################### CAMPOS ###########################
    private val ID_PERMISOS = 1
    private lateinit var mMap: GoogleMap
    private var coordenadas: LatLng? = null
    private lateinit var bd: BDFuentes
    private var idFuente: Int = Constantes.NO_HAY_FUENTE
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val ZOOM_CAMARA = 16.5f

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Obtener el SupportMapFragment y recibir notificacion cuando el mapa este listo para usarse
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        // Obtener el elemento necesario para obtener la ubicacion.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        // Cargar los eventos de boton.
        eventosBoton()

        // Obtener la instancia de la BD.
        bd = BDFuentes.getInstancia(this)

        // Intentar obtener el idFuente si hubiese una actividad anterior.
        getIdFuenteActividadAnterior()
    }


    // ########################### AL ESTAR LISTO EL MAPA ###########################
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        // Asignarlo al campo y asignar el escuchador a esta clase para controlar los eventos al pulsar el marcador.
        mMap = googleMap

        // Requerir los permisos GPS si no los tiene.
        if (!tienePermisosGPS()) {
            requerirPermisos()
        }

        // Hacer visible el boton de localizar.
        btn_localizar.visibility = View.VISIBLE

        // Si no hay fuente carga todas y si la hay muestra solo esa.
        if (idFuente == Constantes.NO_HAY_FUENTE) {
            cargarTodasLasFuentes()
            lblDescripcion.text = getText(R.string.todasLasFuentes)
        } else {
            val fuente = bd.fuenteDao().getFuente(idFuente)
            addMarcador(traducirCoordenadas(fuente.coordenadas), fuente.nombre)
            Log.e("COORDENADA", fuente.toString())
            lblDescripcion.text = fuente.nombre
        }

    }


    // ########################### TRAS REQUERIR LOS PERMISOS ###########################
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ID_PERMISOS && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // ESTAN EN REGLA LOS PERMISOS
                alPulsarBotonLocalizar()

            } else {
                mostrarTexto(getText(R.string.no_permisos).toString())
            }
        }
    }


    // ########################### METODOS DE PERMISOS ###########################
    // Pedir permisos, muestra un mensaje.
    private fun requerirPermisos() {
        val permisos = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
        )
        ActivityCompat.requestPermissions(this, permisos, ID_PERMISOS)
    }

    // Comprueba si esta activo el GPS y se puede acceder a internet.
    private fun isActivoGPSyWifi(): Boolean {
        val controlLoc = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return controlLoc.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // Retorna true si tiene permisos para usar el GPS.
    private fun tienePermisosGPS(): Boolean {
        val permisoLocalizacion = ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val permisoLocalizacion2 = ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return permisoLocalizacion && permisoLocalizacion2
    }

    // ########################### METODOS BOTONES Y ACCIONES ###########################
    private fun eventosBoton() {
        btn_localizar.setOnClickListener { alPulsarBotonLocalizar() }
    }

    private fun alPulsarBotonLocalizar() {
        getMiUbicacion()
        posicionarCamara(coordenadas)
    }


    // ########################### METODOS GOOGLE MAPS ###########################
    @SuppressLint("MissingPermission")
    private fun getMiUbicacion() {
        if (tienePermisosGPS() && isActivoGPSyWifi()) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = false

            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                val localizacion = task.result

                if (localizacion != null) {
                    this@MapsActivity.coordenadas =
                        LatLng(localizacion.latitude, localizacion.longitude)
                }
            }
        } else if (!tienePermisosGPS()) {
            mostrarCuadroDialogoPermisos()
        } else {
            mostrarTexto(getString(R.string.asegurate_gps_wifi))
        }
    }

    private fun posicionarCamara(coordenadas: LatLng?) {
        // Mover la camara hacia el marcador.
        if (coordenadas != null) {
            val posicionCamara: CameraPosition =
                CameraPosition.Builder().target(coordenadas).zoom(ZOOM_CAMARA).bearing(0f).build()
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(posicionCamara))
        }
    }

    private fun addMarcador(coordenadas: LatLng, descripcion: String) {
        mMap.addMarker(
            MarkerOptions()
                .position(coordenadas).title(descripcion)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 13f))
    }

    // ########################### METODOS PARA CARGAR LAS FUENTES ###########################
    private fun cargarTodasLasFuentes() {
        val fuentes: List<Fuente> = bd.fuenteDao().getFuentes()

        for (fuente in fuentes) {
            addMarcador(traducirCoordenadas(fuente.coordenadas), fuente.nombre)
        }
    }

    private fun traducirCoordenadas(Coordenadacadena: String): LatLng {
        val latLong = Coordenadacadena.trim().split(",")
        return LatLng(latLong[0].toDouble(), latLong[1].toDouble())
    }

    private fun getIdFuenteActividadAnterior() {
        val idFuenteRecibida = intent.extras?.getInt("idFuente")

        if (idFuenteRecibida != null) {
            idFuente = idFuenteRecibida
        }
    }

    // ########################### METODOS AUXILIARES ###########################
    private fun mostrarTexto(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarCuadroDialogoPermisos() {
        // Construir el cuadro de dialogo
        val constructor: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val vistaDialogo: View = inflater.inflate(R.layout.dialog_accept, null)
        constructor.setView(vistaDialogo)
        val cuadroDialogo: AlertDialog = constructor.create()

        // Hacer que el fondo del layout sea invisible
        cuadroDialogo.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Definir los textos
        vistaDialogo.lblDialogTitulo.text = getText(R.string.no_permisos)
        vistaDialogo.lblDialogMensaje.text = getText(R.string.necesario_gps_wifi)
        val btnDialogoAceptar: Button = vistaDialogo.btnDialogAceptar
        btnDialogoAceptar.text = getText(R.string.conceder)
        val btnDialogoRechazar: Button = vistaDialogo.btnDialogDenegar
        btnDialogoRechazar.text = getText(R.string.denegar)

        // Controlar los eventos de boton
        btnDialogoAceptar.setOnClickListener {
            requerirPermisos()
            cuadroDialogo.dismiss()
        }
        btnDialogoRechazar.setOnClickListener { cuadroDialogo.dismiss() }

        // Mostrar el cuadro de dialogo
        cuadroDialogo.show()
    }

}