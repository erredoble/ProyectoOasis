package com.erredoble.oasis.modelo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erredoble.oasis.modelo.entidad.Ciudad


@Dao
interface CiudadDao {
    /**
     * Interfaz de Ciudad.
     * Contiene los metodos ""CRUD".
     */
    @Query("SELECT * FROM ciudad")
    fun getCiudades(): List<Ciudad>

    @Query(value = "SELECT * FROM ciudad WHERE id_ciudad = :id_ciudad")
    fun getCiudad(id_ciudad: Int): Ciudad

    @Insert
    fun addCiudad(ciudad: Ciudad)
}