package com.eeema.android.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eeema.android.data.model.Character

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun character(id: Int): Character

    @Query("SELECT * FROM characters WHERE profession LIKE '%' || :filter || '%' ORDER BY id ASC")
    fun characters(filter: String): PagingSource<Int, Character>

    @Query("DELETE FROM characters")
    suspend fun deleteCharacters()
}
