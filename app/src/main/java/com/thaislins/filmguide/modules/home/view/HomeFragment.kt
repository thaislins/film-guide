package com.thaislins.filmguide.modules.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thaislins.filmguide.databinding.FragmentHomeBinding
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.model.MovieType
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter
import com.thaislins.filmguide.modules.home.view.adapter.ImagePagerAdapter
import com.thaislins.filmguide.modules.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import java.util.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by inject()
    private var timer: Timer = Timer()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this
        setAdapters()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.show()
        binding.viewModel?.loadFilms(MovieType.TRENDING.ordinal)
        binding.viewModel?.loadFilms(MovieType.POPULAR.ordinal)
        binding.viewModel?.loadFilms(MovieType.NOWPLAYING.ordinal)
        binding.viewModel?.loadFilms(MovieType.TOPRATED.ordinal)
        observeTrendingFilms()
    }

    private fun setAdapters() {
        with(binding) {
            rvPopularFilms.adapter = FilmAdapter(mutableListOf<Film?>(), context!!)
            rvPopularFilms.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNowPlaying.adapter = FilmAdapter(mutableListOf<Film?>(), context!!)
            rvNowPlaying.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTopRated.adapter = FilmAdapter(mutableListOf<Film?>(), context!!)
            rvTopRated.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeTrendingFilms() {
        homeViewModel.trendingFilms.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                pager?.adapter = ImagePagerAdapter(context!!, it.take(5))
            }
        })
    }

    private fun addAutomaticScroll() {
        pager?.let {
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    pager?.post(Runnable {
                        val currentItem = pager?.currentItem
                        val previousItem = pager?.adapter?.count?.minus(1)
                        if (currentItem != null && previousItem != null) {
                            if (currentItem < previousItem) {
                                pager?.currentItem = pager?.currentItem?.plus(1)!!
                            } else {
                                pager?.currentItem = 0
                            }
                        }
                    })
                }
            }

            timer = Timer()
            timer.schedule(timerTask, 1500, 1500)
        }
    }

    override fun onResume() {
        super.onResume()
        addAutomaticScroll()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
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
