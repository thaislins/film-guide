package com.thaislins.filmguide.modules.film.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thaislins.filmguide.R
import com.thaislins.filmguide.databinding.FragmentFilmBinding
import com.thaislins.filmguide.modules.film.viewmodel.FilmViewModel
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter

class FilmFragment : Fragment() {

    private var loading = false
    private var item = 0
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

        val filter =
            arguments?.getInt(resources.getString(R.string.list_filter_item_key))
        filmViewModel.filmType.value = filter
    }

    override fun onStart() {
        super.onStart()
        filmViewModel.loadDBFilms()
        addOnScrollListener()
        observeLoading()
    }

    fun observeLoading() {
        filmViewModel.loading.observe(viewLifecycleOwner, Observer {
            loading = it
        })
    }

    private fun addOnScrollListener() {
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        binding.rvFilms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    loading = true
                    binding.viewModel?.loadMoreFilms()
                    //Do pagination.. i.e. fetch new data
                }

            }
        })
    }
}
