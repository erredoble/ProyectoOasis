package com.erredoble.oasis.modelo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erredoble.oasis.modelo.entidad.Localizacion

@Dao
interface LocalizacionDao {
    @Query("SELECT * FROM Localizacion")
    fun getLocalizaciones(): List<Localizacion>


    @Query(value = "SELECT * FROM localizacion WHERE id_loc = :id_loc")
    fun getLocalizacion(id_loc : Int) : Localizacion

    @Insert
    fun addLocalizacion(localizacion: Localizacion)
}