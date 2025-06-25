package com.mypackage.sportsevents.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mypackage.sportsevents.data.local.dao.SportsDao
import com.mypackage.sportsevents.data.remote.api.SportsApi
import com.mypackage.sportsevents.data.repository.SportsRepositoryImpl
import com.mypackage.sportsevents.domain.repository.SportsRepository
import com.mypackage.sportsevents.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://ios-kaizen.github.io/"
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideSportsApi(gson: Gson): SportsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SportsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSportsRepository(api: SportsApi, sportsDao: SportsDao, networkHelper: NetworkHelper): SportsRepository {
        return SportsRepositoryImpl(api, sportsDao, networkHelper)
    }
}
