package com.amatucci.andrea.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.amatucci.andrea.pokedex.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val pokemonViewModel: PokemonViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.model = pokemonViewModel
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)

        setupViews()
    }

    private fun setupViews()
    {
        // Finding the Navigation Controller
        val navController = findNavController(R.id.nav_host_fragment)

        // Setting Navigation Controller with the BottomNavigationView
        binding.bottomNavigation.setupWithNavController(navController)
    }
}