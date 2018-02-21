package br.com.cristianodp.vocabularylist.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.cristianodp.vocabularylist.R

import kotlinx.android.synthetic.main.activity_dashboard.*
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import br.com.cristianodp.vocabularylist.adapters.RecyclerLessonAdapter
import br.com.cristianodp.vocabularylist.ado.generateFirebaseId
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class DashboardActivity : AppCompatActivity() {

    //firabase
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    private val RC_SIGN_IN = 1

    private lateinit var userId:String
    private lateinit var mRecyclerLessonAdapter: RecyclerLessonAdapter

    val manager = supportFragmentManager
    val TAG = "DashboardActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setSupportActionBar(toolbar)

        initBottomMenu(savedInstanceState)

        initFirebaseAuth()

    }

    private fun initFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null){
                onSignedInInitialize(user.uid)
            }
            else{
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(
                                        Arrays.asList<AuthUI.IdpConfig>(AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .build(),
                        RC_SIGN_IN)
            }
        }
    }

    private fun onSignedInInitialize(uid: String) {
        this.userId = uid
        initContent(0)
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
    }
    private fun initBottomMenu(savedInstanceState: Bundle?) {
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState)
        spaceNavigationView.addSpaceItem(SpaceItem(resources.getString(R.string.tab_home), R.drawable.ic_home_black_24dp))
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_library_add_black_24dp)
        spaceNavigationView.addSpaceItem(SpaceItem(resources.getString(R.string.tab_library), R.drawable.ic_library_books_black_24dp))
        spaceNavigationView.setSpaceOnClickListener(object : SpaceOnClickListener {
            override fun onCentreButtonClick() {
                callAddCollection()
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                initContent(itemIndex)
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                Log.v(TAG, itemIndex.toString() + " " + itemName)
            }
        })
    }

    private fun initContent(itemIndex: Int) {
        when (itemIndex) {
            0 -> showFragmentHome()
            1 -> showFragmentLibrary()
        }
    }

    private fun callAddCollection() {
        val i = Intent(this@DashboardActivity,CollectionActivity::class.java)
        i.putExtra("userId",userId)
        i.putExtra("collectionId",generateFirebaseId())
        i.putExtra("isFormEditable",true)
        startActivity(i)
    }

    fun showFragmentHome(){
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment().newInstance(userId)
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }

    fun showFragmentLibrary(){
        val transaction = manager.beginTransaction()
        val fragment = LibraryFragment()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        spaceNavigationView.onSaveInstanceState(outState);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mmu_logoff -> {
                AuthUI.getInstance().signOut(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }




}
