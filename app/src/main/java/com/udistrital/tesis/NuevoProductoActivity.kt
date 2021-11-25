package com.udistrital.tesis

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoProductoActivity : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        var nombreCientifico: String? = null

        if (intent.hasExtra("especie")) {
            val especie = intent.extras?.getSerializable("especie") as Especie

            nombre_et.setText(especie.nombreCientifico)
            nombre_com_et.setText(especie.nombreComun)
            familia_et.setText(especie.familia)
            hoja_et.setText(especie.hoja)
            filo_et.setText(especie.filotaxia)
            estipula_et.setText(especie.estipula)
            exudado_et.setText(especie.exudado)
            flor_et.setText(especie.flor)
            Fruto_et.setText(especie.fruto)
            descripcion_et.setText(especie.descripcion)
            nombreCientifico = especie.nombreCientifico
            val imageUri = ImageController.getImageUri(this, nombreCientifico.toString())
            imageSelect_iv.setImageURI(imageUri)
        }

        val database = AppDatabase.getDatabase(this)

        save_btn.setOnClickListener {
            val nombreCien = nombre_et.text.toString()
            val nombreComun = nombre_com_et.text.toString()
            val familia = familia_et.text.toString()
            val hoja = hoja_et.text.toString()
            val filota = filo_et.text.toString()
            val esti = estipula_et.text.toString()
            val exud = exudado_et.text.toString()
            val flores = flor_et.text.toString()
            val frutos = Fruto_et.text.toString()
            val descripc = descripcion_et.text.toString()

            val especie = Especie(nombreCien, nombreComun, familia, hoja, filota, esti,
                exud, flores, frutos, descripc, R.drawable.ic_launcher_background)

            if (nombreCientifico != null) {
                CoroutineScope(Dispatchers.IO).launch {

                    database.especies().update(especie)

                    imageUri?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImageController.saveImage(this@NuevoProductoActivity, nombreCientifico , it)
                    }

                    this@NuevoProductoActivity.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    database.especies().insertAll(especie)

                    imageUri?.let {
                        ImageController.saveImage(this@NuevoProductoActivity, especie.nombreCientifico, it)
                    }

                    this@NuevoProductoActivity.finish()
                }
            }
        }

        imageSelect_iv.setOnClickListener {
            ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data

                imageSelect_iv.setImageURI(imageUri)
            }
        }
    }
}