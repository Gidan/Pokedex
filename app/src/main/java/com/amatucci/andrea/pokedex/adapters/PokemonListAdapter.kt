package com.amatucci.andrea.pokedex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amatucci.andrea.pokedex.R

import com.amatucci.andrea.pokedex.databinding.ListItemPokemonBinding
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.util.PokemonDataUtil
import com.bumptech.glide.Glide

class PokemonListAdapter : ListAdapter<Pokemon, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            ListItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = getItem(position)
        (holder as PokemonViewHolder).bind(pokemon)
    }

    class PokemonViewHolder(private val binding: ListItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon){
            binding.apply {
                txtPokemonName.text = item.name
                txtNum.text = binding.root.context.getString(R.string.pokemon_item_number, item.id)
                val imgUrl = PokemonDataUtil.getArtwork(item)
                Glide.with(binding.root)
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_pokeball)
                    .into(ivPokemonArtwork)
            }
        }
    }

}

private class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}