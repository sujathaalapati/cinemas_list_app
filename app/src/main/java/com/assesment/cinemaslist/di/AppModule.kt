package com.assesment.cinemaslist.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.assesment.cinemaslist.data.network.Api
import com.assesment.cinemaslist.repository.CinemaRepository
import com.assesment.cinemaslist.repository.CinemaRepositoryImpl
import com.assesment.cinemaslist.util.Constants
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

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): Api {
        return retrofitBuilder
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        api: Api
    ) = CinemaRepositoryImpl(api) as CinemaRepository

    /*@Singleton
    @Provides
    fun provideMovieRepository(
        api: Api
    ): MovieRepository{
        return MovieRepositoryImpl(api)
    }*/

}