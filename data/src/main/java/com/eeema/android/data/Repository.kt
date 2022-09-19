package com.eeema.android.data

import androidx.paging.PagingData
import com.eeema.android.data.model.Character
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getCharacters(filter: String): Flow<PagingData<Character>>
    suspend fun getCharacter(id: Int): Character
}
