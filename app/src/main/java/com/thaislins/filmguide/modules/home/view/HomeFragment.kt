package com.thaislins.filmguide.modules.home.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.AppDatabase
import com.thaislins.filmguide.core.MovieFilter
import com.thaislins.filmguide.databinding.FragmentHomeBinding
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter
import com.thaislins.filmguide.modules.home.view.adapter.ImagePagerAdapter
import com.thaislins.filmguide.modules.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import java.util.*

class HomeFragment : Fragment() {

    private var timer: Timer = Timer()
    private val appDatabase: AppDatabase by inject()
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_search).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.show()
        setAdapters()
        load()
        setPullToRefresh()
    }

    fun setPullToRefresh() {
        swipeRefresh.setOnRefreshListener(OnRefreshListener {
            clearDB()
            load()
            swipeRefresh.setRefreshing(false)
        })
    }

    private fun load() {
        homeViewModel.loadAllFilms()
        observeTrendingFilms()
    }

    private fun clearDB() = runBlocking {
        GlobalScope.async { appDatabase.clearAllTables() }.await()
    }

    private fun setAdapters() {
        with(binding) {
            rvPopularFilms.adapter = FilmAdapter(mutableListOf<Film?>(), context!!, false)
            rvPopularFilms.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNowPlaying.adapter = FilmAdapter(mutableListOf<Film?>(), context!!, false)
            rvNowPlaying.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTopRated.adapter = FilmAdapter(mutableListOf<Film?>(), context!!, false)
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
                            } else { // If item is last then set it to current
                                pager?.currentItem = 0
                            }
                        }
                    })
                }
            }

            timer = Timer()
            timer.schedule(timerTask, 3000, 3000)
        }
    }

    private fun setButtonsClickListeners() {
        btnPopularFilms.setOnClickListener { goToFilmFragment(MovieFilter.POPULAR.ordinal) }
        btnTopRated.setOnClickListener { goToFilmFragment(MovieFilter.TOPRATED.ordinal) }
        btnNowPlaying.setOnClickListener { goToFilmFragment(MovieFilter.NOWPLAYING.ordinal) }
    }

    private fun goToFilmFragment(movieFilter: Int) {
        val bundle = Bundle()
        bundle.putInt(
            resources.getString(R.string.list_filter_item_key),
            movieFilter
        )

        Navigation.findNavController(view!!).navigate(R.id.toFilmFragment, bundle)
    }

    override fun onStart() {
        super.onStart()
        setButtonsClickListeners()
    }

    override fun onResume() {
        super.onResume()
        addAutomaticScroll()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}
