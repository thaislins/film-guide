package com.thaislins.filmguide.modules.details.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thaislins.filmguide.core.AdapterContract
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
    }

    override fun set(list: List<Any>?) {
        if (!list.isNullOrEmpty()) {
            trailers = (list as List<Video?>)
        }
    }

    private fun onItemClick(v: View?, holder: RecyclerView.ViewHolder) {
        //
    }

    inner class ViewHolder(private val binding: ItemTrailerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(trailer: Video) {
            binding.trailer = trailer
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            onItemClick(v, this)
        }
    }
}