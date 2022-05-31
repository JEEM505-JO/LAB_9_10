package com.uni.lab9_10.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uni.lab9_10.Model
import com.uni.lab9_10.R
import com.uni.lab9_10.databinding.ItemContactBinding
import com.uni.lab9_10.ui.AdapterContact.ViewHolderModel

class AdapterContact : ListAdapter<Model, ViewHolderModel>(ModelComparator()) {

    class ViewHolderModel(view: View) : RecyclerView.ViewHolder(view) {
        private val bindig = ItemContactBinding.bind(view)
        fun bind(model: Model) {
            val bundle : Bundle = Bundle()
            bundle.putString("Numero",model.Numero)
            bundle.putString("Nombre",model.Nombre)
            bundle.putString("Referencia",model.Referencia)
            bundle.putInt("ID",model.id)
            bindig.itemNumero.text = model.Numero
            bindig.itemnombre.text = model.Nombre
            bindig.itemReferencia.text = model.Referencia
            bindig.itemupdate.setOnClickListener {
                it.findNavController().navigate(R.id.action_contact2_to_addContact,bundle)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolderModel {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contact, parent, false)
                return ViewHolderModel(view)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderModel {
        return ViewHolderModel.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolderModel, position: Int) {
        val itemposition = getItem(position)
        holder.bind(itemposition)
    }

}

private class ModelComparator : DiffUtil.ItemCallback<Model>() {

    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean =
        oldItem.Numero == newItem.Numero

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean =
        oldItem == newItem
}





