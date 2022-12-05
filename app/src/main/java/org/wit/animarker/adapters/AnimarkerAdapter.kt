package org.wit.animarker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.animarker.databinding.CardAnimarkerBinding
import org.wit.animarker.models.AnimarkerModel

interface AnimarkerListener {
    fun onAnimarkerClick(animarker: AnimarkerModel)
}

class AnimarkerAdapter constructor(private var animarkers: List<AnimarkerModel>,
                                   private val listener: AnimarkerListener) :
    RecyclerView.Adapter<AnimarkerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAnimarkerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val animarker = animarkers[holder.adapterPosition]
        holder.bind(animarker, listener)
    }

    override fun getItemCount(): Int = animarkers.size

    class MainHolder(private val binding : CardAnimarkerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animarker: AnimarkerModel, listener: AnimarkerListener) {
            binding.animarkerTitle.text = animarker.title
            binding.description.text = animarker.description
            binding.destination.text= animarker.destination
            binding.animarkerDate.text= animarker.date
            Picasso.get().load(animarker.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onAnimarkerClick(animarker) }
        }
    }
}