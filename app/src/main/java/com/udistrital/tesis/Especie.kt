package com.udistrital.tesis

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "especies")
class Especie(
    @PrimaryKey()
    val nombreCientifico:String,

    val nombreComun: String,

    val familia: String,

    val hoja:String,

    val filotaxia: String,

    val estipula: String,

    val exudado: String,

    val flor: String,

    val fruto: String,

    val descripcion: String,

    val imagen: Int,

) : Serializable