package com.erredoble.oasis.modelo.configuracion

/**
 * Clase abstracta que contiene todos los valores de la aplicacion, con el fin de que este centralizado.
 * Estos son valores que no van a variar (constantes).
 * Companion obliga a que los metodos y campos sean propios de la clase(static)
 */

abstract class Constantes {
    companion object {
        const val NOMBRE_BD = "bd.fuentes"
        const val NO_HAY_FUENTE = -1
    }
}
