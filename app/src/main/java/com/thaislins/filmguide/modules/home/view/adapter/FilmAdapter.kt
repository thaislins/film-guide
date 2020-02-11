package com.thaislins.filmguide.modules.home.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.AdapterContract
import com.thaislins.filmguide.core.POSTER_URL
import com.thaislins.filmguide.databinding.ItemFilmBinding
import com.thaislins.filmguide.modules.home.model.Film
import java.util.*

class FilmAdapter(
    var films: MutableList<Film?>,
    private var context: Context,
    private var showDetails: Boolean
) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>(),
    AdapterContract {

    private var lastPosition = -1
    private val loadedFilms = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        films[position]?.let { holder.bind(it) }
        val options: RequestOptions =
            RequestOptions().placeholder(R.drawable.no_image_found).error(R.drawable.no_image_found)

        holder.filmImage?.let {
            Glide.with(context)
                .load(POSTER_URL + films[position]?.posterPath).apply(options)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(it)
        }

        if (films[position]?.isWatched!!)
            holder.filmImage?.setColorFilter(
                ContextCompat.getColor(
                    context,
                    R.color.tint
                )
            )

        if (showDetails) {
            holder.layoutDetails?.visibility = View.VISIBLE
        }
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(
        viewToAnimate: View,
        position: Int
    ) { // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration =
                Random().nextInt(501).toLong() //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun set(list: List<Any>?) {
        if (list != null) {
            val filteredList = (list as List<Film>).filter { it.id !in loadedFilms }

            if (films.isNullOrEmpty()) {
                films = list.toMutableList()
            } else {
                films.addAll(filteredList)
            }
            loadedFilms.addAll(filteredList.map { it.id })
            notifyDataSetChanged()
        }
    }

    private fun onItemClick(holder: RecyclerView.ViewHolder) {
        val pos = holder.adapterPosition
        if (pos == RecyclerView.NO_POSITION) return

        Navigation.findNavController(holder.itemView).navigate(
            R.id.toDetailsFragment, createBundle(context, pos)
        )
    }

    private fun createBundle(context: Context, pos: Int): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(context.resources.getString(R.string.film_item_key), films[pos])
        return bundle
    }

    inner class ViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        internal val filmImage: ImageView? = itemView.findViewById(R.id.ivFilm)
        internal val layoutDetails: ConstraintLayout? = itemView.findViewById(R.id.layoutDetails)
        fun bind(film: Film) {
            binding.root.setOnClickListener(this)
            binding.film = film
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            onItemClick(this)
        }
    }
}