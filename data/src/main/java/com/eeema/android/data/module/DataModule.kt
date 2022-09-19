package com.eeema.android.data.module

import android.content.Context
import androidx.room.Room
import com.eeema.android.data.BuildConfig
import com.eeema.android.data.CharactersRepository
import com.eeema.android.data.Repository
import com.eeema.android.data.datasource.local.LocalDB
import com.eeema.android.data.datasource.remote.RemoteApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideNetworkApi(): RemoteApi {

        val client = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
        }.build()

        val gson = GsonBuilder().create()

        return Retrofit.Builder()
            .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(RemoteApi::class.java)
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): LocalDB {
        return Room.databaseBuilder(
            appContext,
            LocalDB::class.java,
            "OompaLoompaDB"
        ).build()
    }

    @Provides
    fun provideRepository(repository: CharactersRepository): Repository {
        return repository
    }
}
