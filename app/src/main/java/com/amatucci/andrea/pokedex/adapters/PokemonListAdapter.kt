package com.amatucci.andrea.pokedex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amatucci.andrea.pokedex.R
import com.amatucci.andrea.pokedex.customviews.Type
import com.amatucci.andrea.pokedex.customviews.TypeTag
import com.amatucci.andrea.pokedex.databinding.ListItemPokemonBinding
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.util.PokemonDataUtil
import com.amatucci.andrea.pokedex.util.blendColors
import com.bumptech.glide.Glide
import java.util.*

interface OnItemClickListener {
    fun onItemClicked(position : Int, commonView : View)
}

class PokemonListAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Pokemon, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            ListItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = getItem(position)
        (holder as PokemonViewHolder).bind(pokemon)
    }

    class PokemonViewHolder(private val binding: ListItemPokemonBinding, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: Pokemon){
            binding.apply {
                txtPokemonName.text = item.name.toUpperCase(Locale.getDefault())
                txtNum.text = root.context.getString(R.string.pokemon_item_number, item.id)
                val imgUrl = PokemonDataUtil.getArtwork(item)
                Glide.with(root)
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_pokeball)
                    .into(ivPokemonArtwork)

                llTypes.removeAllViews()
                item.types.forEach {
                    val typeTag = TypeTag(root.context)
                    typeTag.setType(it.type.name)
                    llTypes.addView(typeTag)
                }
                val map = item.types.map { it.type.name }.map { ContextCompat.getColor(root.context, Type.valueOf(it).colorRes) }
                val blendColors = blendColors(*map.toIntArray())
                pokemonBlendColor.setBackgroundColor(blendColors!!)
            }
            binding.cvPokemon.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            onItemClickListener.onItemClicked(position, binding.ivPokemonArtwork)
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