package com.ufs.csiq6823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, SeasonListFragment())
            }
        }
    }

    fun openPlayList(weekTitle: String) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, PlayListFragment.newInstance(weekTitle))
            addToBackStack(null)
        }
    }

    fun openAddPlayer() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, AddPlayerFragment())
            addToBackStack(null)
        }
    }

    fun openAddGame(weekTitle: String) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, AddGameFragment.newInstance(weekTitle))
            addToBackStack(null)
        }
    }

    fun openGameDetails(white: String, black: String) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, GameDetailsFragment.newInstance(white, black))
            addToBackStack(null)
        }
    }
}
