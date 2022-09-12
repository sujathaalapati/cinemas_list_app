package com.assesment.cinemaslist.ui.cinema.list

sealed class CinemaListEvent {

    object RestoreStateEvent : CinemaListEvent()
    object GetCinemaListEvent : CinemaListEvent()
}
