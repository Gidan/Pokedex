package com.amatucci.andrea.pokedex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.network.PokemonService
import com.amatucci.andrea.pokedex.room.PokemonDatabase
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val db : PokemonDatabase,
    private val networkService : PokemonService
) : RemoteMediator<Int, Pokemon>() {

    private val dao = db.pokemonDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null

                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.id + 1
                }
            }

            val response = networkService.pokemonList(
                offset = loadKey, limit = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            )

            val pokemonList = ArrayList<Pokemon>()
            response.results.forEach{
                val pokemon = networkService.pokemon(it.name)
                pokemonList.add(pokemon)
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }

                dao.insertAll(pokemonList)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.next == null
            )
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}