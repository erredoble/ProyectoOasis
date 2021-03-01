package com.erredoble.oasis.modelo.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erredoble.oasis.modelo.configuracion.Constantes
import com.erredoble.oasis.modelo.entidad.Area
import com.erredoble.oasis.modelo.entidad.Ciudad
import com.erredoble.oasis.modelo.entidad.Fuente

/**
 * Clase encargada de controlar la BD.
 * Para poder crear una instancia hay que llamar al metodo "getInstancia()" de esta clase.
 * Si quieres aniadir una nueva entidad tienes que meter la clase en el array de "entities" y
 * despues aniadir el metodo abstracto abajo para poder llamar a sus metodos.
 */

@Database(
    entities = [Ciudad::class, Area::class, Fuente::class],
    version = 1
)
abstract class BDFuentes : RoomDatabase() {

    /** Metodos abstractos para manipular las entidades de la BD. */
    abstract fun ciudadDao(): CiudadDao
    abstract fun areaDao(): AreaDao
    abstract fun fuenteDao(): FuenteDao

    /**
     * Este fragmento de codigo construye y retorna una instancia de este objeto.
     * Solo habra una instancia para toda la aplicacion.
     * Companion obliga a que los metodos y campos sean propios de la clase(static)
     */
    companion object {
        @Volatile
        private var instanciaBD: BDFuentes? = null

        /** Este es el metodo que construye y devuelve una sola instancia de esta clase. */
        fun getInstancia(context: Context): BDFuentes {
            val instanciaAuxUno =
                instanciaBD

            if (instanciaAuxUno != null) {
                return instanciaAuxUno
            }

            // Si no existe una instancia de la BD, se creara.
            synchronized(this) {
                val instanciaAux = Room.databaseBuilder(
                    context,
                    BDFuentes::class.java,
                    Constantes.NOMBRE_BD
                ).createFromAsset("fuentes.bd").allowMainThreadQueries().build()

                instanciaBD = instanciaAux
                return instanciaAux
            }

        }
    }

}
