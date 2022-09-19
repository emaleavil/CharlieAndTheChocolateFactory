package com.eeema.android.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eeema.android.data.model.Character
import com.eeema.android.data.model.RemoteKey

@Database(entities = [Character::class, RemoteKey::class], version = 1)
abstract class LocalDB : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
