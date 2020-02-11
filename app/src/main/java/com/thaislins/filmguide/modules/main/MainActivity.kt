package com.thaislins.filmguide.modules.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.thaislins.filmguide.R
import com.thaislins.filmguide.core.Connectivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val network = Connectivity(applicationContext)
        network.registerNetworkCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val searchAction = menu?.findItem(R.id.action_search)
        menu?.findItem(R.id.search_view)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }
}
