package com.assesment.cinemaslist.ui.cinema.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.assesment.cinemaslist.model.Cinema
import com.assesment.cinemaslist.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CinemaListViewModel
@Inject
constructor(
    private val repository: CinemaRepository
    ): ViewModel(){

    private val _cinema : MutableLiveData<PagingData<Cinema>> = MutableLiveData()
    val cinemas : LiveData<PagingData<Cinema>> = _cinema

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
            repository
                .getAllMovies()
                .cachedIn(viewModelScope)
                .collect { _cinema.value = it  }
        }
    }

}