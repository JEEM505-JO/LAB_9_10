package com.uni.lab9_10.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.uni.lab9_10.R
import com.uni.lab9_10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar = view.findViewById<Toolbar>(R.id.include)
        setSupportActionBar(toolbar)
        val togle = ActionBarDrawerToggle(
            this,
            binding.drawerlayout,toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerlayout.addDrawerListener(togle)
        togle.syncState()
        toolbar.setOnMenuItemClickListener{item ->
            when(item.itemId){
                R.id.search -> {
                    val search = item.actionView as SearchView
                    search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            Toast.makeText(applicationContext,"BUSCAR: $query",Toast.LENGTH_SHORT).show()
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }

                    })
                    true
                }
                else -> {
                    false
                }
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         super.onOptionsItemSelected(item)

        when(item.itemId){
            R.id.addContact -> {
                findNavController(R.id.drawerlayout).navigate(R.id.action_contact2_to_addContact)
            }
        }
        return true

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

}