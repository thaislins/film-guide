package com.thaislins.filmguide.modules.film.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thaislins.filmguide.databinding.FragmentFilmBinding
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
        binding.rvFilms.adapter = FilmAdapter(emptyList(), this.context!!)

        with(binding.rvFilms) {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 3)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmViewModel.loadFilms()
    }
}
