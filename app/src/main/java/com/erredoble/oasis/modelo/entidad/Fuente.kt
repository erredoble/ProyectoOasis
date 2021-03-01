package com.erredoble.oasis.modelo.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/**
 * Modelo Fuente.
 * Claves ajenas (FK) - id_area(Area)
 * Restricciones - Si, ha de existir al menos una Area
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Area::class,
        parentColumns = ["id_area"],
        childColumns = ["area_id"]
    )]
)
data class Fuente(
    @PrimaryKey val id_fuente: Int,
    @ColumnInfo(name = "descripcion") var descripcion: String,
    @ColumnInfo(name = "coordenadas") val coordenadas: String,
    @ColumnInfo(name = "area_id") val area_id: Int
) {
    override fun toString(): String {
        return "Fuente\n" +
                " - id_fuente=$id_fuente\n" +
                " - descrip_loc=$descripcion\n" +
                " - coordenadas='$coordenadas'\n" +
                " - area_id=$area_id"
    }
}