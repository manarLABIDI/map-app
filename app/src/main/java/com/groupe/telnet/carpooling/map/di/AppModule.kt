package com.groupe.telnet.carpooling.map.di

import android.content.Context
import com.groupe.telnet.carpooling.map.data.remote.api.SearchApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.config.Configuration
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideOSRMRoadManager(@ApplicationContext context: Context): OSRMRoadManager {
        val userAgent = Configuration.getInstance().userAgentValue
        return OSRMRoadManager(context, userAgent)
    }

    @Provides
    @Singleton
    fun providesNominatimApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun providesRideRepository(): RideRepository {
//        return RideRepository(  )
//    }
//    @Provides
//    @Singleton
//    fun providesRideRequestUseCase(repository: RideRepository): RideRequestUseCase {
//        return RideRequestUseCase(repository)
//    }
}
