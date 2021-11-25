package com.udistrital.tesis

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductosDao {
    @Query("SELECT * FROM especies")
    fun getAll(): LiveData<List<Especie>>

    @Query("SELECT * FROM especies WHERE nombreCientifico = :id")
    fun get(id: String): LiveData<Especie>

    @Insert
    fun insertAll(vararg especies: Especie)

    @Update
    fun update(especie: Especie)

    @Delete
    fun delete(especie: Especie)
}