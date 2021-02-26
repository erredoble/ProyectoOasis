package com.erredoble.oasis.modelo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erredoble.oasis.modelo.entidad.Fuente

/**
 * Interfaz de Fuente.
 * Contiene los metodos "CRUD".
 */
@Dao
interface FuenteDao {
    @Query("SELECT * FROM fuente")
    fun getFuentes(): List<Fuente>

    @Query("select f.* from fuente f join localizacion l on f.loc_id = l.id_loc where l.area_id = :id_area")
    fun getFuentes(id_area : Int): List<Fuente>

    @Query(value = "SELECT * FROM fuente WHERE id_fuente = :id_fuente")
    fun getFuente(id_fuente: Int): Fuente

    @Insert
    fun addFuente(fuente: Fuente)


}