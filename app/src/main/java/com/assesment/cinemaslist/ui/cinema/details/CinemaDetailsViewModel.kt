package com.assesment.cinemaslist.ui.cinema.details

import androidx.lifecycle.*
import com.assesment.cinemaslist.model.CinemaDetails
import com.assesment.cinemaslist.repository.CinemaRepository
import com.assesment.cinemaslist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CinemaDetailsViewModel
@Inject
constructor(
    private val repository: CinemaRepository
): ViewModel() {

    private val _cinemaDetails : MutableLiveData<Resource<CinemaDetails>> = MutableLiveData()
    val cinemaDetails : LiveData<Resource<CinemaDetails>> = _cinemaDetails

    /*init {
        getMovieDetails(movieId)
    }*/

    fun setMovieId(movieId: Int) {
        getMovieDetails(movieId)
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository
                .getMovieDetails(movieId)
                .collect {
                    _cinemaDetails.value = it
                }
        }
    }
}