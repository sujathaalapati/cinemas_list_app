package com.assesment.cinemaslist.util

import androidx.recyclerview.widget.DiffUtil
import com.assesment.cinemaslist.model.Cinema

object MovieDiffCallback : DiffUtil.ItemCallback<Cinema>() {

    override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem == newItem
    }
}