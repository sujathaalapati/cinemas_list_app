package com.assesment.cinemaslist.ui.cinema.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.assesment.cinemaslist.databinding.CinemaItemBinding
import com.assesment.cinemaslist.model.Cinema
import com.assesment.cinemaslist.util.MovieDiffCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CinemaRecyclerAdapter
@Inject
constructor(
    @ApplicationContext val context: Context,
    private val clicked: (Int) -> Unit
    ): PagingDataAdapter<Cinema, CinemaRecyclerAdapter.MovieViewHolder>(
        MovieDiffCallback
    ) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            CinemaItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class MovieViewHolder(
        private val binding: CinemaItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Cinema?) {
            binding.title.text = data?.title
            binding.rating.text = data?.rating
            binding.country.text = data?.country

            Glide.with(binding.root)
                .load(data?.poster)
                .placeholder(circularProgressBar())
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .into(binding.poster)

            binding.root.setOnClickListener {
                data?.id?.let { id -> clicked.invoke(id) }
            }

        }

        private fun circularProgressBar(): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 10f
            circularProgressDrawable.centerRadius = 40f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }
    }
}