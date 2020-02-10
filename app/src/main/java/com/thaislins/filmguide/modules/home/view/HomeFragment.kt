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
import com.thaislins.filmguide.databinding.FragmentHomeBinding
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter
import com.thaislins.filmguide.modules.home.view.adapter.ImagePagerAdapter
import com.thaislins.filmguide.modules.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    private var timer: Timer = Timer()
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
            load()
            swipeRefresh.setRefreshing(false)
        })
    }

    private fun load() {
        homeViewModel.loadAllFilms()
        observeTrendingFilms()
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
                            } else {
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
        btnPopularFilms.setOnClickListener { goToFilmFragment(homeViewModel.popularFilms.value!!) }
        btnTopRated.setOnClickListener { goToFilmFragment(homeViewModel.topRated.value!!) }
        btnNowPlaying.setOnClickListener { goToFilmFragment(homeViewModel.nowPlaying.value!!) }
    }

    private fun goToFilmFragment(list: List<Film>) {
        val array = arrayListOf<Film>()
        array.addAll(list)

        val bundle = Bundle()
        bundle.putParcelableArrayList(
            resources.getString(R.string.list_film_item_key),
            array
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
