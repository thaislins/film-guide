package com.thaislins.filmguide.modules.film.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thaislins.filmguide.databinding.FragmentFilmBinding
import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.model.MovieType
import com.thaislins.filmguide.modules.film.view.adapter.FilmAdapter
import com.thaislins.filmguide.modules.film.view.adapter.ImagePagerAdapter
import com.thaislins.filmguide.modules.film.viewmodel.FilmViewModel
import kotlinx.android.synthetic.main.fragment_film.*
import org.koin.android.ext.android.inject

class FilmFragment : Fragment() {

    private val filmViewModel: FilmViewModel by inject()
    private lateinit var binding: FragmentFilmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmBinding.inflate(inflater, container, false)
        binding.viewModel = filmViewModel
        binding.lifecycleOwner = this
        binding.rvPopularFilms.adapter = FilmAdapter(mutableListOf<Film?>(), this.context!!)

        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        with(binding.rvPopularFilms) {
            layoutManager = horizontalLayoutManagaer
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.loadFilms(MovieType.TRENDING.ordinal)
        binding.viewModel?.loadFilms(MovieType.TOPRATED.ordinal)
        observeTrendingFilms()
        //addOnScrollListener()
    }

    private fun observeTrendingFilms() {
        filmViewModel.trendingFilms.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                pager.adapter = ImagePagerAdapter(context!!, it.take(5))
                addAutomaticScroll()
            }
        })
    }

    private fun addAutomaticScroll() {
        val autoHandler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (pager.currentItem < pager.adapter!!.count - 1) {
                    pager.currentItem = pager.currentItem + 1
                    autoHandler.postDelayed(this, 1500)
                } else {
                    pager.currentItem = 0
                    autoHandler.postDelayed(this, 1500)
                }
            }
        }
        autoHandler.postDelayed(runnable, 1500)
    }

    /*private fun addOnScrollListener() {
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        binding.rvFilms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.getChildCount()
                totalItemCount = layoutManager.getItemCount()
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                    binding.viewModel?.loadFilms()
                }
            }
        })
    }*/
}
