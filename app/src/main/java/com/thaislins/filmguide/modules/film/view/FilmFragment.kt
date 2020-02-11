package com.thaislins.filmguide.modules.film.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.isNetworkConnected
import com.thaislins.filmguide.databinding.FragmentFilmBinding
import com.thaislins.filmguide.modules.film.viewmodel.FilmViewModel
import com.thaislins.filmguide.modules.home.model.Film
import com.thaislins.filmguide.modules.home.view.adapter.FilmAdapter

class FilmFragment : Fragment() {

    private var loading = false
    private val filter by lazy { arguments?.getInt(resources.getString(R.string.list_filter_item_key)) }
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
        observeSearchResults()
        binding.rvFilms.adapter = FilmAdapter(mutableListOf<Film?>(), context!!, true)
        binding.rvFilms.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        filmViewModel.filmType.value = filter
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        var searchView: SearchView? = null
        menu.findItem(R.id.action_search).isVisible = false
        if (filter == -1) {

            val searchItem = menu.findItem(R.id.search_view)
            menu.findItem(R.id.search_view).isVisible = true
            searchView = MenuItemCompat.getActionView(searchItem) as SearchView
            searchView.setIconifiedByDefault(false)
            searchView.requestFocus()

            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    search(newText)
                    return false
                }
            })
        } else menu.findItem(R.id.search_view).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                pressBackButton()
                return true
            }
        }

        return false
    }

    private fun pressBackButton() {
        view?.findNavController()?.navigateUp()
    }

    fun search(newText: String) {
        if (isNetworkConnected) {
            if (!filmViewModel.searchResults.value.isNullOrEmpty()) {
                filmViewModel.searchResults.value!!.clear()
            }

            if (newText != "") {
                filmViewModel.searchFilms(newText)
            }
        }
    }

    private fun observeSearchResults() {
        filmViewModel.searchResults.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                (binding.rvFilms.adapter as FilmAdapter).films = it as MutableList<Film?>
                (binding.rvFilms.adapter as FilmAdapter).notifyDataSetChanged()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        filmViewModel.loadDBFilms()
        if (filter != -1) {
            addOnScrollListener()
            observeLoading()
        }
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
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
