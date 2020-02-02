package com.thaislins.filmguide.modules.film.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thaislins.filmguide.databinding.FragmentFilmBinding
import com.thaislins.filmguide.modules.film.model.Film
import com.thaislins.filmguide.modules.film.view.adapter.FilmAdapter
import com.thaislins.filmguide.modules.film.viewmodel.FilmViewModel
import org.koin.android.ext.android.inject

class FilmFragment : Fragment() {

    private val filmViewModel: FilmViewModel by inject()
    private lateinit var binding: FragmentFilmBinding
    private val numOfColumns = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmBinding.inflate(inflater, container, false)
        binding.viewModel = filmViewModel
        binding.lifecycleOwner = this
        binding.rvFilms.adapter = FilmAdapter(mutableListOf<Film?>(), this.context!!)

        with(binding.rvFilms) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, numOfColumns)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.loadFilms()
        addOnScrollListener()
    }

    private fun addOnScrollListener() {
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
    }
}
