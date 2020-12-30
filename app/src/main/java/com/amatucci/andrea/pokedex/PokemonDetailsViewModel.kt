package com.amatucci.andrea.pokedex

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import io.uniflow.androidx.flow.AndroidDataFlow
import kotlinx.coroutines.launch
import java.util.*

class PokemonDetailsViewModel(private val pokemonRepository: PokemonRepository) : AndroidDataFlow() {

    private var _selectedPokemonName = MutableLiveData<String>()
    private var _selectedPokemonId = MutableLiveData<String>()
    private val _selectedPokemon = MutableLiveData<Pokemon?>()

    var selectedPokemonName : LiveData<String>
    var selectedPokemonId : LiveData<String>
    var selectedPokemonWeight : LiveData<String>
    var selectedPokemonHeight : LiveData<String>
    val type1 = MutableLiveData<String>()
    val type2 = MutableLiveData<String>()
    private val selectedPokemon: LiveData<Pokemon?> get() = _selectedPokemon

    val stats = MutableLiveData<List<Pair<String, Float>>>()

    init {
        selectedPokemonName = Transformations.map(_selectedPokemonName){
            it?.toUpperCase(Locale.getDefault())
        }
        selectedPokemonId = Transformations.map(_selectedPokemonId){
            if (it != null) "#${it}" else ""
        }

        selectedPokemonWeight = Transformations.map(selectedPokemon){
            if (it != null) "${it.weight / 10.0} kg" else ""
        }
        selectedPokemonHeight = Transformations.map(selectedPokemon){
            if (it != null) "${it.height / 10.0} m" else ""
        }

        _selectedPokemon.observeForever { it ->
            stats.value = it?.stats?.map { Pair(it.stat.name, it.base_stat.toFloat()) }
        }
    }

    fun setIntent(intent : Intent) {
        intent.extras?.let {
            _selectedPokemonName.value = it.get("name") as String
            val id = it.get("id") as Int
            _selectedPokemonId.value = id.toString()

            type1.value = it.get("type1") as String
            if (it.containsKey("type2")){
                type2.value = it.get("type2") as String
            }

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