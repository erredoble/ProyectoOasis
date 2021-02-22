package com.erredoble.oasis.modelo.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Modelo Ciudad.
 * Claves ajenas (FK) - No
 * Restricciones - No
 */
@Entity
data class Ciudad(
    @PrimaryKey val id_ciudad: Int,
    @ColumnInfo(name = "nombre_ciudad") val nombre_ciudad: String
) {
    override fun toString(): String {
        return "Ciudad:\n" +
                " - id_ciudad=$id_ciudad\n" +
                " - nombre_ciudad='$nombre_ciudad'"
    }
}