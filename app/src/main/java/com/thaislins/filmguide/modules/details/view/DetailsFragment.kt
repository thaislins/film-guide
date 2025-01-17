package com.thaislins.filmguide.modules.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.BACKDROP_URL
import com.thaislins.filmguide.databinding.FragmentDetailsBinding
import com.thaislins.filmguide.modules.details.view.adapter.VideoAdapter
import com.thaislins.filmguide.modules.details.viewmodel.DetailsViewModel
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private var film: Film? = null
    private val detailsViewModel = DetailsViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = detailsViewModel
        binding.rvSimilarMovies.adapter = FilmAdapter(mutableListOf<Film?>(), context!!, false)
        binding.rvSimilarMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvTrailers.adapter = VideoAdapter(emptyList(), context!!)
        binding.rvTrailers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        film = arguments?.getParcelable(resources.getString(R.string.film_item_key))
        detailsViewModel.getFilmDetails(film)
        pressBackButton()
        ivMoviePoster.let {
            Glide.with(context!!)
                .load(BACKDROP_URL + film?.backdropPath).into(it)
        }
    }

    private fun pressBackButton() {
        btnReturn.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }
    }
}
