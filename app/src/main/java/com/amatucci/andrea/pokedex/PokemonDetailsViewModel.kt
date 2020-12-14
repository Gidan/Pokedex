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
    val type1 = MutableLiveData<String>()
    val type2 = MutableLiveData<String>()
    val selectedPokemon: LiveData<Pokemon?> get() = _selectedPokemon

    init {
        selectedPokemonName = Transformations.map(_selectedPokemonName){
            it?.toUpperCase(Locale.getDefault())
        }
        selectedPokemonId = Transformations.map(_selectedPokemonId){
            if (it != null) "#${it}" else ""
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