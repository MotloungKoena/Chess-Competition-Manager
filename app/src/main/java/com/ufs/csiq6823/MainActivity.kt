package com.ufs.csiq6823

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(savedInstanceState=${savedInstanceState != null})")
        setContentView(R.layout.activity_main)

        findViewById<MaterialToolbar>(R.id.toolbar)?.let {
            setSupportActionBar(it)
            Log.d(TAG, "Toolbar set as SupportActionBar")
        }

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, dest, args ->
            Log.d(TAG, "Destination = ${dest.label}, args=$args")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp()")
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


    fun openPlayList(weekTitle: String, dateLabel: String? = null) {
        Log.i(TAG, "openPlayList(weekTitle='$weekTitle', dateLabel='$dateLabel')")
        val args = bundleOf(
            "weekTitle" to weekTitle,
            "dateLabel" to dateLabel
        )
        navController.navigate(R.id.action_seasonList_to_playList, args)
    }

    fun openAddPlayer() {
        Log.i(TAG, "openAddPlayer()")
        navController.navigate(R.id.action_seasonList_to_addPlayer)
    }

    fun openNewWeek() {
        Log.i(TAG, "openNewWeek()")
        navController.navigate(R.id.action_seasonList_to_newWeek)
    }

    fun openAddGame(weekTitle: String) {
        Log.i(TAG, "openAddGame(weekTitle='$weekTitle')")
        val args = bundleOf("weekTitle" to weekTitle)
        navController.navigate(R.id.action_playList_to_addGame, args)
    }

    fun openGameDetails(white: String, black: String) {
        Log.i(TAG, "openGameDetails(white='$white', black='$black')")
        val args = bundleOf(
            "white" to white,
            "black" to black
        )
        navController.navigate(R.id.action_playList_to_gameDetails, args)
    }
}
