package com.amatucci.andrea.pokedex

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import com.amatucci.andrea.pokedex.util.PokemonDataUtil
import io.uniflow.androidx.flow.AndroidDataFlow
import kotlinx.coroutines.launch
import java.util.*

class PokemonDetailsViewModel(private val pokemonRepository: PokemonRepository) : AndroidDataFlow() {

    private var _selectedPokemonName = MutableLiveData<String>()
    private var _selectedPokemonId = MutableLiveData<String>()
    private val _selectedPokemon = MutableLiveData<Pokemon?>()

    var selectedPokemonName = Transformations.map(_selectedPokemonName){
        it?.toUpperCase(Locale.getDefault())
    }

    var selectedPokemonId = Transformations.map(_selectedPokemonId){
        if (it != null) "#${it}" else ""
    }

    var selectedPokemonWeight = Transformations.map(selectedPokemon){
        if (it != null) "${it.weight / 10.0} kg" else ""
    }

    var selectedPokemonHeight = Transformations.map(selectedPokemon){
        if (it != null) "${it.height / 10.0} m" else ""
    }

    val type1 = MutableLiveData<String>()
    val type2 = MutableLiveData<String>()
    private val selectedPokemon: LiveData<Pokemon?> get() = _selectedPokemon

    val stats = MutableLiveData<List<Pair<String, Float>>>()
    val imageUrl = MutableLiveData<String>()

    init {
        _selectedPokemon.observeForever { it ->
            _selectedPokemonId.value = it?.id.toString()
            _selectedPokemonName.value = it?.name

            val typesCount = it?.typesCount() ?: 0
            type1.value = it?.types?.get(0)?.type?.name
            type2.value = if (typesCount > 1) it?.types?.get(1)?.type?.name else null

            stats.value = it?.stats?.map { Pair(it.stat.name, it.base_stat.toFloat()) }

            imageUrl.value = PokemonDataUtil.getArtwork(it!!)
        }
    }

    fun setIntent(intent : Intent) {
        intent.extras?.let {
            val id = it.get("id") as Int
            selectPokemon(id)
        }
    }

    private fun selectPokemon(id: Int){
        viewModelScope.launch {
            val pokemon = pokemonRepository.pokemon(id)
            _selectedPokemon.postValue(pokemon)
        }
    }
}