package com.amatucci.andrea.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.Type
import com.amatucci.andrea.pokedex.ui.theme.PokedexTheme
import com.amatucci.andrea.pokedex.util.PokemonDataUtil
import com.amatucci.andrea.pokedex.util.blendedColor
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainComposeActivity : ComponentActivity() {
    private val pokemonViewModel: PokemonViewModel by viewModel()

    @OptIn(ExperimentalPagingApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(pokemonViewModel.fullPokemonList)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(fullPokemonList: Pager<Int, Pokemon>) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Pokedex", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            )
        }, bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    IconButton(onClick = {
                        navController.navigate("pokedex") {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pokeball),
                            "pokedex screen",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("credits") {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.nav_menu_credits),
                            "credits screen",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        // Screen content
        NavHost(
            navController,
            startDestination = Screen.Pokedex.route,
            Modifier.padding(contentPadding)
        ) {
            composable(Screen.Pokedex.route) { Pokedex(fullPokemonList) }
            composable(Screen.Credits.route) { Credits() }
        }
        Box(modifier = Modifier.padding(contentPadding)) { }
    }
}

@Preview
@Composable
fun PreviewCredits() {
    PokedexTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Credits()
        }
    }
}


@Composable
fun Credits() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.pokeball), contentDescription = "")
            Text("Pokedex", fontSize = 30.sp)
            Text("by Andrea Amatucci")
        }
    }
}

@Composable
fun Pokedex(pager: Pager<Int, Pokemon>) {
    val pokemonList = pager.flow.collectAsLazyPagingItems()
    PokemonList(pokemonList)
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Pokedex : Screen("pokedex", R.string.title_pokedex)
    object Credits : Screen("credits", R.string.title_credits)
}

@Composable
fun PokemonList(pokemon: LazyPagingItems<Pokemon>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(items = pokemon, key = { message -> message.id }) { p ->
            p?.let {
                PokemonListItem(pokemon = p)
            }
        }
    }
}

@Composable
fun TypeBadge(type: Type) {
    val colorType = com.amatucci.andrea.pokedex.customviews.Type.valueOf(type.type.name)
    val typeColor = colorResource(id = colorType.colorRes)
    Text(
        text = type.type.name.uppercase(),
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .wrapContentWidth()
            .background(typeColor, CircleShape)
            .padding(10.dp, 0.dp)
    )
}


@Composable
fun PokemonListItem(pokemon: Pokemon) {
    Card(
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier
            .height(132.dp)
            .fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val blendColor =
                blendedColor(pokemon, LocalContext.current)?.let { Color(it) } ?: Color.White
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(7.dp)
                    .clip(RectangleShape)
                    .background(blendColor)
            )
            Box(modifier = Modifier.padding(10.dp, 0.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(PokemonDataUtil.getArtwork(pokemon))
                        .crossfade(true)
                        .build(),

                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(100.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(PaddingValues(0.dp, 10.dp, 10.dp, 10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = pokemon.name.uppercase())
                    Text(text = pokemon.id.toString())
                }
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    pokemon.types.map { TypeBadge(it) }
                }
            }
        }
    }
}