package com.assesment.cinemaslist.repository

import androidx.paging.PagingData
import com.assesment.cinemaslist.model.Cinema
import com.assesment.cinemaslist.model.CinemaDetails
import com.assesment.cinemaslist.util.Resource
import kotlinx.coroutines.flow.Flow

interface CinemaRepository {

    fun getAllMovies(): Flow<PagingData<Cinema>>
    fun getMovieDetails(movieId: Int): Flow<Resource<CinemaDetails>>
}