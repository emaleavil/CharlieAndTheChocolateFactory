package com.eeema.android.data.datasource.remote

import com.eeema.android.data.model.Page
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {
    @GET("oompa-loompas")
    suspend fun characters(@Query("page") page: Int? = null): Page
}
