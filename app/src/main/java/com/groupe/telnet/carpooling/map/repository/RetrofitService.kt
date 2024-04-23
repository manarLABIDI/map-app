package com.groupe.telnet.carpooling.map.repository

import com.groupe.telnet.carpooling.map.searchLocation.LocationResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit { // Provide Retrofit instance
        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNominatimApi(retrofit: Retrofit): NominatimApi {
        return retrofit.create(NominatimApi::class.java)
    }
}
