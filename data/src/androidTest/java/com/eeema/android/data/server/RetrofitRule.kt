package com.eeema.android.data.server

import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRule<Service>(
    url: HttpUrl,
    serviceClass: Class<Service>,
) : TestRule {

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .create()

    val service = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(serviceClass)

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        override fun evaluate() {
            base?.evaluate()
        }
    }
}
