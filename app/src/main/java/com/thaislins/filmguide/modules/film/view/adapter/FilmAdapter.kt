package com.thaislins.filmguide.modules.film.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thaislins.filmguide.BuildConfig.POSTER_URL
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.AdapterContract
import com.thaislins.filmguide.databinding.ItemFilmBinding
import com.thaislins.filmguide.modules.film.model.Film

class FilmAdapter(private var films: List<Film?>, private var context: Context) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>(),
    AdapterContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        films[position]?.let { holder.bind(it) }
        holder.filmImage?.let {
            Glide.with(context).load(POSTER_URL + films[position]?.posterPath).into(it)
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

    class ViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal var filmImage: ImageView? = itemView.findViewById(R.id.ivFilm)

        fun bind(film: Film) {
            binding.film = film
            binding.executePendingBindings()
        }
    }

    override fun set(list: List<Film>?) {
        if (list != null) {
            this.films = list
            notifyDataSetChanged()
        }
    }
}