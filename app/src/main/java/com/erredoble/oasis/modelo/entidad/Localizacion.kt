package com.erredoble.oasis.modelo.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/**
 * Modelo Localizacion.
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
data class Localizacion(
    @PrimaryKey val id_loc: Int,
    @ColumnInfo(name = "descrip_loc") var descrip_loc: String?,
    @ColumnInfo(name = "coordenadas") val coordenadas: String?,
    @ColumnInfo(name = "area_id") val area_id: Int
) {
    override fun toString(): String {
        return "Localizacion:id_loc=$id_loc\n" +
                " - descrip_loc=$descrip_loc\n" +
                " - coordenadas='$coordenadas'\n" +
                " - area_id=$area_id"
    }
}