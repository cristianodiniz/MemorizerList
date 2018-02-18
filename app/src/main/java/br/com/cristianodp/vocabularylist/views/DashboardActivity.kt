package br.com.cristianodp.vocabularylist.views

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.cristianodp.vocabularylist.R

import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setSupportActionBar(toolbar)



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mmu_search -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showFragmentHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                showFragmentLibrary()
                return@OnNavigationItemSelectedListener true
            }
            /*R.id.navigation_add -> {
                showFragmentAdd()
                return@OnNavigationItemSelectedListener true
            }*/
        }
        false
    }
    fun showFragmentHome(){
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showFragmentLibrary(){
        val transaction = manager.beginTransaction()
        val fragment = LibraryFragment()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showFragmentAdd(){
        val transaction = manager.beginTransaction()
        val fragment = AddFragment()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
