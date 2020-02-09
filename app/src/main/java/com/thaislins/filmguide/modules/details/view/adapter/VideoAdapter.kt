package com.thaislins.filmguide.modules.details.view.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.AdapterContract
import com.thaislins.filmguide.core.YOUTUBE_THUMBNAIL_URL
import com.thaislins.filmguide.core.YOUTUBE_URL
import com.thaislins.filmguide.databinding.ItemTrailerBinding
import com.thaislins.filmguide.modules.details.model.Video

class VideoAdapter(private var trailers: List<Video?>, private var context: Context) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder>(),
    AdapterContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrailerBinding.inflate(inflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        trailers[position]?.let { holder.bind(it) }

        holder.trailerThumnail?.let {
            Glide.with(context).load(getThumnailUrl(trailers[position])).into(it)
        }
    }

    private fun getThumnailUrl(trailer: Video?): String {
        if (trailer?.site == "YouTube") {
            return String.format(YOUTUBE_THUMBNAIL_URL, trailer.key);
        } else {
            return ""
        }
    }

    override fun set(list: List<Any>?) {
        if (!list.isNullOrEmpty()) {
            trailers = (list as List<Video?>)
            notifyDataSetChanged()
        }
    }

    private fun onItemClick(v: View?, holder: RecyclerView.ViewHolder) {
        val pos = holder.adapterPosition
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailers[pos]?.key))
        val webIntent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                String.format(YOUTUBE_URL, trailers[pos]?.key)
            )
        )

        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }

    inner class ViewHolder(private val binding: ItemTrailerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        internal val trailerThumnail: ImageView? = itemView.findViewById(R.id.ivTrailer)

        fun bind(trailer: Video) {
            binding.root.setOnClickListener(this)
            binding.trailer = trailer
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            onItemClick(v, this)
        }
    }
}