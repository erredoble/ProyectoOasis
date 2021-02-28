package com.erredoble.oasis.controlador

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.core.app.ActivityCompat
import com.erredoble.oasis.R
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

/**
 * Link Google Maps videotutorial: https://www.youtube.com/watch?v=3-84hvpb_zA
 * Capa KML - https://developers.google.com/maps/documentation/android-sdk/utility/kml?hl=es-419
 * Obtener longitud y latitud (10:53): https://www.youtube.com/watch?v=ari3iD-3q8c
 *
 * TAREAS Y PROBLEMAS:
 * - No se ve el mapa.
 * - Si el GPS no esta activo y tiene permisos no funcionara, comprueba que el GPS esta encendido.
 * - Si no se conceden permisos que notifique con un mensaje y que salga de la actividad.
 */

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    // ########################### CAMPOS ###########################
    private lateinit var mMap: GoogleMap
    private var coordenadas: LatLng = LatLng(0.0, 0.0)
    private var marcador: Marker? = null

    // ########################### METODO ON CREATE ###########################
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Quitar barra superior que sale por defecto con el nombre de la APP.
        supportActionBar?.hide()

        // Cargar los eventos de boton.
        eventosBoton()
    }


    // ########################### METODO AL ESTAR LISTO EL MAPA ###########################
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
        mMap = googleMap

        // Obtener localizacion actual.
        comprobarPermisos()

        // Hacer visible el boton de localizar.
        btn_localizar.visibility = View.VISIBLE

        // Add markers
        addmarkers()
    }

    // ########################### METODOS DE PERMISOS ###########################
    @SuppressLint("MissingPermission")
    private fun comprobarPermisos() {
        // Mostrar mi ubicacion actual.
        if (!tienePermisosGPS()) {
            requerirPermisos()
        } else {
            // Mostrar boton localizacion (GoogleMaps arriba derecha).
            mMap.setMyLocationEnabled(true)
        }
    }

    // Pedir permisos, muestra un mensaje.
    private fun requerirPermisos() {
        val permisos = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permisos, 1)
    }

    // Retorna true si tiene permisos para usar el GPS.
    private fun tienePermisosGPS(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLatitudLongitud() {
        // Definir valores de geolocalizacion.
        val peticionLocalizacion = LocationRequest()
        peticionLocalizacion.interval = 10000
        peticionLocalizacion.fastestInterval = 3000
        peticionLocalizacion.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        // Sobreescribir metodo de la clase LocationCallback.
        class LocationCallbackPersonalizado() : LocationCallback() {
            override fun onLocationResult(resultadoLoc: LocationResult) {
                super.onLocationResult(resultadoLoc)
                LocationServices.getFusedLocationProviderClient(this@MapsActivity)
                    .removeLocationUpdates(this)
                if (resultadoLoc.locations.size > 0) {
                    val ultimoIndice = resultadoLoc.locations.size - 1

                    val latitud = resultadoLoc.locations[ultimoIndice].latitude
                    val longitud = resultadoLoc.locations[ultimoIndice].longitude

                    // Pasarle el valor al campo de la clase MapsActivity.
                    this@MapsActivity.coordenadas = LatLng(latitud, longitud)
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(this@MapsActivity)
            .requestLocationUpdates(
                peticionLocalizacion,
                LocationCallbackPersonalizado(),
                Looper.getMainLooper()
            )
    }

    // ########################### METODOS ###########################
    private fun eventosBoton() {
        btn_localizar.setOnClickListener {
            getLatitudLongitud()
            moverCamaraMapa()
            lblCoordenadas.text = coordenadas.toString()
        }
    }

    private fun moverCamaraMapa() {
        // Eliminar todos los marcadores.
        marcador?.remove()


        // Aniadir el marcador y posicionar la camara.
        marcador = mMap.addMarker(MarkerOptions().position(coordenadas).title("Aquí estoy"))

        // Mover la camara hacia el marcador.
        val posicionCamara: CameraPosition =
            CameraPosition.Builder().target(coordenadas).zoom(5.5f).bearing(5f).build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(posicionCamara))
    }

    private fun addmarkers() {
        val puntoInicio = LatLng(42.4647, -2.4458)
        mMap.addMarker(MarkerOptions().position(puntoInicio).title("Marker in Plaza del Espolon"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoInicio, 13f))

        //PARQUE DEL EBRO
        val fuenteCuartoPuenteLocation = LatLng(42.4752, -2.4616)
        val fuenteCuartoPuente = mMap.addMarker(
            MarkerOptions()
                .position(fuenteCuartoPuenteLocation).title("Fuente Cuarto Puente")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )
        val fuenteCanchaBaloncestoPosition = LatLng(42.4731, -2.4606)
        val fuenteCanchaBasket = mMap.addMarker(
            MarkerOptions()
                .position(fuenteCanchaBaloncestoPosition).title("Fuente cancha de baloncesto")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )
        val fuentePinoPinonieroPosition = LatLng(42.4698, -2.4572)
        val fuentePinoPinoniero = mMap.addMarker(
            MarkerOptions()
                .position(fuentePinoPinonieroPosition).title("Fuente del pino piñonero")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )
        val fuenteNuevaSeccionFemeninaPosition = LatLng(42.4696, -2.4551)
        val fuenteNuevaSeccionFemenina = mMap.addMarker(
            MarkerOptions()
                .position(fuenteNuevaSeccionFemeninaPosition).title("Fuente nueva de la antigua seccion femenina")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )
        val fuenteViejaSeccionFemeninaPosition = LatLng(42.4692, -2.4548)
        val fuenteViejaSeccionFemenina = mMap.addMarker(
            MarkerOptions()
                .position(fuenteViejaSeccionFemeninaPosition).title("Fuente vieja de la antigua seccion femenina")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )
        val fuenteMuroDelCuartelPosition = LatLng(42.4685, -2.4523)
        val fuenteMuroDelCuartel = mMap.addMarker(
            MarkerOptions()
                .position(fuenteMuroDelCuartelPosition).title("Fuente junto al antiguo muro del cuartel")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fuente_marcador))
        )


    }


}