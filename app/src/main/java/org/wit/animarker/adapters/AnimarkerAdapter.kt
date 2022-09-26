package org.wit.animarker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.animarker.databinding.CardAnimarkerBinding
import org.wit.animarker.models.AnimarkerModel

class AnimarkerAdapter constructor(private var animarkers: List<AnimarkerModel>) :
    RecyclerView.Adapter<AnimarkerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAnimarkerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val animarker = animarkers[holder.adapterPosition]
        holder.bind(animarker)
    }

    override fun getItemCount(): Int = animarkers.size

    class MainHolder(private val binding : CardAnimarkerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animarker: AnimarkerModel) {
            binding.animarkerTitle.text = animarker.title
            binding.description.text = animarker.description
        }
    }
}