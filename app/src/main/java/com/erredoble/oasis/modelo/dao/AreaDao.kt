package com.erredoble.oasis.modelo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erredoble.oasis.modelo.entidad.Area

/**
 * Interfaz de Area.
 * Contiene los metodos que se emplearan para acceder, modificar o borrar en la BD.
 */
@Dao
interface AreaDao {
    @Query("SELECT * FROM area")
    fun getAreas(): List<Area>

    @Query(value = "SELECT * FROM area WHERE id_area = :id_area")
    fun getArea(id_area: Int): Area

    @Insert
    fun addArea(area: Area)
}