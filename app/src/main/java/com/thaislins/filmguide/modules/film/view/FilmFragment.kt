package com.thaislins.filmguide.modules.film.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thaislins.filmguide.R
import com.thaislins.filmguide.databinding.FragmentFilmBinding
import com.thaislins.filmguide.modules.film.viewmodel.FilmViewModel
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter

class FilmFragment : Fragment() {

    private val filmViewModel: FilmViewModel by lazy {
        ViewModelProvider(this).get(FilmViewModel()::class.java)
    }

    private lateinit var binding: FragmentFilmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFilmBinding.inflate(inflater, container, false)
        binding.viewModel = filmViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFilms.adapter = FilmAdapter(mutableListOf<Film?>(), context!!, true)
        binding.rvFilms.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val filmList =
            arguments?.getParcelableArrayList<Film>(resources.getString(R.string.list_film_item_key))
        filmViewModel.films.value = filmList

    }
}
