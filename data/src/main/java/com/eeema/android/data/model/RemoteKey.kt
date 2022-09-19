package com.eeema.android.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_keys"
)
data class RemoteKey(
    @ColumnInfo(name = "next_page_id") val nextPage: Int?,
    @ColumnInfo(name = "prev_page_id") val prevPage: Int?,
    @ColumnInfo(name = "id") @PrimaryKey val id: Int
)
