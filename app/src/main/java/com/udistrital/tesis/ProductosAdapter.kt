package com.udistrital.tesis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_producto.view.*

class ProductosAdapter(private val mContext: Context, private val listaEspecies: List<Especie>) :  RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return  ViewHolder(mContext, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val park = listaEspecies[position]
        holder.bind(park)
    }

    override fun getItemCount(): Int {
        return listaEspecies.size
    }


    class ViewHolder(private val mContext: Context, val view : View) : RecyclerView.ViewHolder(view){
        private var nombreCienTextView : TextView = view.findViewById(R.id.nombre_item)
        private var nombreComTextView : TextView = view.findViewById(R.id.nombre_com_item)
        private var nombreFamiTextView : TextView = view.findViewById(R.id.familia_item)
        private var pictureImageView : ImageView = view.findViewById(R.id.imageView)


        fun bind(especie : Especie){
            nombreCienTextView.text = especie.nombreCientifico
            nombreComTextView.text = especie.nombreComun
            nombreFamiTextView.text = especie.familia.toString()

            pictureImageView
            val imageUri = ImageController.getImageUri(mContext, especie.nombreCientifico)
            pictureImageView.setImageURI(imageUri)
            view.setOnClickListener{
                val intent = Intent(mContext, ProductoActivity::class.java)
                intent.putExtra("id", especie.nombreCientifico)
                mContext.startActivity(intent)
            }


        }

    }
}
