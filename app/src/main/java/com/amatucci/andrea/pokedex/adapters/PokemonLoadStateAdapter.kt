package com.amatucci.andrea.pokedex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amatucci.andrea.pokedex.R
import com.amatucci.andrea.pokedex.databinding.LoadStateBarBinding

class PokemonLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PokemonLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = PokemonLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: PokemonLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error || loadState is LoadState.NotLoading
    }

}

class PokemonLoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.load_state_bar, parent, false)
) {
    private val binding = LoadStateBarBinding.bind(itemView)
    private val errorMsg: TextView = binding.txtState
    private val retry: Button = binding.btnRetry
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }
}
