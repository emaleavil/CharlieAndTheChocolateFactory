package com.eeema.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "characters"
)
data class Character(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("email") val email: String,
    @SerializedName("age") val age: Int,
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("country") val country: String,
    @SerializedName("height") val height: Int
) {
    val fullName: String
        get() = "$firstName $lastName"
}
