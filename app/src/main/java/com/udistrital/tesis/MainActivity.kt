package com.udistrital.tesis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       var listaProductos = emptyList<Especie>()

        val database = AppDatabase.getDatabase(this)

        database.especies().getAll().observe(this, Observer {
            listaProductos = it

            val productosAdapter = ProductosAdapter(this, listaProductos)
            lista.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = productosAdapter
                setHasFixedSize(false)
            }

            lista.adapter = productosAdapter
        })



        floatingActionButton.setOnClickListener {
            val intent = Intent(this, NuevoProductoActivity::class.java)
            startActivity(intent)
        }
    }
}