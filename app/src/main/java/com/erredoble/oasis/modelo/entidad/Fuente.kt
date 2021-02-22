package com.erredoble.oasis.modelo.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Modelo Fuente.
 * Claves ajenas (FK) - id_localizacion(Localizacion)
 * Restricciones - Si, ha de existir al menos una Localizacion antes de poder aniadir una Fuente.
 */

@Entity(
    foreignKeys = [ForeignKey(
        entity = Localizacion::class,
        parentColumns = ["id_loc"],
        childColumns = ["loc_id"]
    )]
)
data class Fuente(
    @PrimaryKey val id_fuente: Int,
    @ColumnInfo(name = "descrip_fuente") var descrip_fuente: String?,
    @ColumnInfo(name = "foto_fuente") val foto_fuente: String?,
    @ColumnInfo(name = "loc_id") val loc_id: Int
) {
    override fun toString(): String {
        return "Fuente:id_fuente=$id_fuente\n" +
                " - descrip_fuente=$descrip_fuente\n" +
                " - foto_fuente=$foto_fuente\n" +
                " - loc_id=$loc_id"
    }
}