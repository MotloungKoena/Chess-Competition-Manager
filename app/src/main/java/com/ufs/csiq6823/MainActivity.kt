package com.ufs.csiq6823

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialToolbar?>(R.id.toolbar)?.let { setSupportActionBar(it) }
        supportActionBar?.title = getString(R.string.app_name)

        supportFragmentManager.addOnBackStackChangedListener { syncUpButton() }
        syncUpButton()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SeasonListFragment())
                .commit()
        }
    }

    fun openPlayList(weekTitle: String, dateLabel: String? = null) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                PlayListFragment.newInstance(weekTitle, dateLabel)
            )
            .addToBackStack(null)
            .commit()

        supportActionBar?.title = weekTitle
        syncUpButton()
    }

    fun openAddPlayer() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AddPlayerFragment())
            .addToBackStack(null)
            .commit()

        supportActionBar?.title = getString(R.string.title_add_player)
        syncUpButton()
    }

    fun openNewWeek() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewWeekFragment())
            .addToBackStack(null)
            .commit()

        supportActionBar?.title = getString(R.string.title_add_player)
        syncUpButton()
    }

    fun openAddGame(weekTitle: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AddGameFragment.newInstance(weekTitle))
            .addToBackStack(null)
            .commit()

        supportActionBar?.title = getString(R.string.title_add_game)
        syncUpButton()
    }

    fun openGameDetails(white: String, black: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                GameDetailsFragment.newInstance(white, black)
            )
            .addToBackStack(null)
            .commit()

        supportActionBar?.title = getString(R.string.title_game_details)
        syncUpButton()
    }

    private fun syncUpButton() {
        val hasBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(hasBack)

        findViewById<MaterialToolbar?>(R.id.toolbar)?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (!hasBack) {
            supportActionBar?.title = getString(R.string.app_name)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
