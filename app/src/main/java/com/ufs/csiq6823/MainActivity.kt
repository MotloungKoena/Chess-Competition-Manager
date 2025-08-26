package com.ufs.csiq6823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ChessCompetitionManager) // ensures AppCompat theme
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SeasonListFragment())
                .commit()
        }
    }

    fun openPlayList(weekTitle: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, PlayListFragment.newInstance(weekTitle))
            .addToBackStack(null)
            .commit()
    }

    fun openAddPlayer() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AddPlayerFragment())
            .addToBackStack(null)
            .commit()
    }

    fun openAddGame(weekTitle: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AddGameFragment.newInstance(weekTitle))
            .addToBackStack(null)
            .commit()
    }

    fun openGameDetails(white: String, black: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, GameDetailsFragment.newInstance(white, black))
            .addToBackStack(null)
            .commit()
    }
}
