package com.erredoble.oasis.modelo.entidad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Modelo Area.
 * Claves ajenas (FK) - id_ciudad(Ciudad)
 * Restricciones - Si, ha de existir al menos una Ciudad antes de poder aniadir un Area.
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Ciudad::class,
        parentColumns = ["id_ciudad"],
        childColumns = ["ciudad_id"]
    )]
)

data class Area(
    @PrimaryKey val id_area: Int,
    @ColumnInfo(name = "nombre_area") val nombre_area: String,
    @ColumnInfo(name = "ciudad_id") val ciudad_id: Int
) {
    override fun toString(): String {
        return "Area:\n" +
                " - id_area=$id_area\n" +
                " - nombre_area='$nombre_area'\n" +
                " - ciudad_id=$ciudad_id"
    }
}