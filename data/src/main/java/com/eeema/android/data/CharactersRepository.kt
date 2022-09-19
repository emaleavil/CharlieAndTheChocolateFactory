@file:OptIn(ExperimentalPagingApi::class)

package com.eeema.android.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eeema.android.data.datasource.local.LocalDB
import com.eeema.android.data.datasource.remote.CharactersRemoteMediator
import com.eeema.android.data.datasource.remote.RemoteApi
import com.eeema.android.data.model.Character
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CharactersRepository @Inject constructor(
    private val local: LocalDB,
    private val remote: RemoteApi
) : Repository {

    override fun getCharacters(filter: String): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 25, enablePlaceholders = false, prefetchDistance = 3),
        remoteMediator = CharactersRemoteMediator(local, remote)
    ) {
        local.charactersDao().characters(filter)
    }.flow

    override suspend fun getCharacter(id: Int): Character {
        return local.charactersDao().character(id)
    }
}
