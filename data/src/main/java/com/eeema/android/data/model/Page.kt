package com.eeema.android.data.model

import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("results") val data: List<Character> = emptyList(),
    @SerializedName("current") val index: Int,
    @SerializedName("total") val total: Int
)
