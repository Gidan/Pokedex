package com.amatucci.andrea.pokedex.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amatucci.andrea.pokedex.R
import com.amatucci.andrea.pokedex.customviews.Type
import com.amatucci.andrea.pokedex.databinding.ListItemPokemonBinding
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.util.PokemonDataUtil
import com.amatucci.andrea.pokedex.util.blendColors
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import java.util.*


interface OnItemClickListener {
    fun onItemClicked(position: Int, commonView: View, pokemon: Pokemon?)
}

class PokemonListAdapter(private val onItemClickListener: OnItemClickListener) : PagingDataAdapter<Pokemon, RecyclerView.ViewHolder>(
    PokemonDiffCallback()
) {

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

    class PokemonViewHolder(
        private val binding: ListItemPokemonBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var pokemon: Pokemon? = null
        companion object {
            val transitionFactory: DrawableCrossFadeFactory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        }

        fun bind(pokemon: Pokemon?){
            this.pokemon = pokemon
            binding.apply {
                pokemonBlendColor.setBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        android.R.color.transparent
                    )
                )
                pokemon?.let {
                    txtPokemonName.text = pokemon.name.toUpperCase(Locale.getDefault())
                    txtNum.text = root.context.getString(R.string.pokemon_item_number, pokemon.id)
                    val imgUrl = PokemonDataUtil.getArtwork(pokemon)
                    Log.d("PokemonListAdapter", "loading imgUrl $imgUrl")
                    Glide.with(root)
                        .load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivPokemonArtwork)
                    type1.setType(pokemon.types[0].type.name)
                    type2.setType(if (pokemon.types.size > 1) pokemon.types[1].type.name else null)
                    val map = pokemon.types.map { it.type.name }.map { ContextCompat.getColor(
                        root.context, Type.valueOf(
                            it
                        ).colorRes
                    ) }
                    val blendColor = blendColors(*map.toIntArray())

                    bgBlendColor.alpha = 0.0f
                    blendColor?.let {
                        pokemonBlendColor.setBackgroundColor(it)
                        bgBlendColor.setBackgroundColor(it)
                        bgBlendColor.alpha = 0.3f
                    }

                }

            }
            binding.cvPokemon.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition

            onItemClickListener.onItemClicked(position, binding.ivPokemonArtwork, this.pokemon)
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