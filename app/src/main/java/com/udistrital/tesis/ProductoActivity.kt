package com.udistrital.tesis

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var especie: Especie
    private lateinit var especieLiveData: LiveData<Especie>
    private val EDIT_ACTIVITY = 49

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        database = AppDatabase.getDatabase(this)

        val nombreCientifico = intent.getStringExtra("id")

        val imageUri = ImageController.getImageUri(this, nombreCientifico.toString())
        imagen.setImageURI(imageUri)

        especieLiveData = database.especies().get(nombreCientifico.toString())

        especieLiveData.observe(this, Observer {
            especie = it

            nombre_tv.text = especie.nombreCientifico
            nombre_com_tv.text = especie.nombreComun
            familia_tv.text = especie.familia
            hoja_tv.text = especie.hoja
            filo_tv.text = especie.filotaxia
            estipula_tv.text = especie.estipula
            exudado_tv.text = especie.exudado
            flor_tv.text =especie.flor
            Fruto_tv.text = especie.fruto
            descripcion_tv.text = especie.descripcion
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.producto_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_item -> {
                val intent = Intent(this, NuevoProductoActivity::class.java)
                intent.putExtra("especie", especie)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }

            R.id.delete_item -> {
                especieLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.especies().delete(especie)
                    ImageController.deleteImage(this@ProductoActivity, especie.nombreCientifico)
                    this@ProductoActivity.finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imagen.setImageURI(data!!.data)
            }
        }
    }
}
