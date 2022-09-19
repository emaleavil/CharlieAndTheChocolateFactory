@file:OptIn(ExperimentalPagingApi::class)

package com.eeema.android.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.eeema.android.data.datasource.local.LocalDB
import com.eeema.android.data.model.Character
import com.eeema.android.data.model.RemoteKey
import javax.inject.Inject

class CharactersRemoteMediator @Inject constructor(
    private val local: LocalDB,
    private val remote: RemoteApi
) : RemoteMediator<Int, Character>() {

    private val keyDao = local.remoteKeyDao()
    private val charactersDao = local.charactersDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> getRemoteKeyCurrentPosition(state)
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKey?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextPage = remoteKey?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextPage
            }
        }

        return try {
            val endOfPaginationReached = downloadDataAndStore(page, loadType)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun downloadDataAndStore(
        page: Int,
        loadType: LoadType
    ): Boolean {
        val response = remote.characters(page)
        val characters = response.data
        val endOfPaginationReached = response.index == response.total

        local.withTransaction {
            if (loadType == LoadType.REFRESH) {
                keyDao.deleteAllKeys()
                charactersDao.deleteCharacters()
            }

            val keys = characters.map {
                RemoteKey(
                    nextPage = if (endOfPaginationReached) null else page + 1,
                    prevPage = if (page == 1) null else page - 1,
                    id = it.id
                )
            }
            keyDao.insert(keys)
            charactersDao.insertCharacters(characters)
        }
        return endOfPaginationReached
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKey? =
        state.lastItemOrNull()?.let { character -> keyDao.remoteKeyBy(character.id) }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKey? =
        state.firstItemOrNull()?.let { character -> keyDao.remoteKeyBy(character.id) }

    private suspend fun getRemoteKeyCurrentPosition(state: PagingState<Int, Character>): Int =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id -> keyDao.remoteKeyBy(id) }
        }?.nextPage?.minus(1) ?: 1
}
