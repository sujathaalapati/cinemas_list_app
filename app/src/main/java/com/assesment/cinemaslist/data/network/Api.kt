package com.assesment.cinemaslist.data.network

import com.assesment.cinemaslist.data.network.responses.CinemaListResponse
import retrofit2.http.*

interface Api {

    @GET("movies")
    suspend fun getAllMovies(
        @Query("page") page: Int? = null
    ): CinemaListResponse

    @GET("movies/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDto

    @GET("movies")
    suspend fun searchMovies(
        @Query("q") name: String
    ): CinemaListResponse
}