package com.thaislins.filmguide.modules.home.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.BACKDROP_URL
import com.thaislins.filmguide.modules.home.model.Film

class ImagePagerAdapter(private val context: Context, var trendingFilms: List<Film>) :
    PagerAdapter() {

    private val mLayoutInflater: LayoutInflater by lazy {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return trendingFilms.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.item_pager_image, container, false)
        val ivFilm: ImageView = itemView.findViewById(R.id.ivFilm) as ImageView
        ivFilm.let {
            Glide.with(context)
                .load(BACKDROP_URL + trendingFilms[position].backdropPath)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(it)
        }
        container.addView(itemView)

        ivFilm.setOnClickListener {
            Navigation.findNavController(itemView).navigate(
                R.id.toDetailsFragment, createBundle(context, position)
            )
        }

        return itemView
    }

    private fun createBundle(context: Context, pos: Int): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(
            context.resources.getString(R.string.film_item_key),
            trendingFilms[pos]
        )
        return bundle
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as ConstraintLayout)
    }
}