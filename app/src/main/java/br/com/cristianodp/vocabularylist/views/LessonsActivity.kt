package br.com.cristianodp.vocabularylist.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.adapters.RecyclerLessonAdapter
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatadaseADO
import br.com.cristianodp.vocabularylist.ado.LessonADO
import br.com.cristianodp.vocabularylist.global.getPathLessons
import br.com.cristianodp.vocabularylist.models.Lesson
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_lesson_maintenance.*
import kotlinx.android.synthetic.main.activity_lessons.*

import java.util.*

class LessonsActivity : AppCompatActivity() {
    //firabase
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    private val RC_SIGN_IN = 1

    private lateinit var mLessonADO:LessonADO
    private lateinit var userId:String
    private lateinit var mRecyclerLessonAdapter:RecyclerLessonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)
        setSupportActionBar(toolbar)

        initFirebaseAuth()

        fab.setOnClickListener { view ->
            val i = Intent(this@LessonsActivity,LessonMaintenanceActivity::class.java)
            i.putExtra("userId",userId)
            i.putExtra("lessonId",mLessonADO.genereteId())
            startActivity(i)
        }
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
                                                /* new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),*/
                                                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()/*,
                                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()*/))
                                .build(),
                        RC_SIGN_IN)

            }

        }
    }

    private fun onSignedInInitialize(uid: String) {
        this.userId = uid
        initLista()
    }

    private fun initLista() {
        recyclerView.setLayoutManager(LinearLayoutManager(this@LessonsActivity))

        mLessonADO = LessonADO(getPathLessons(userId),"CHILD",object:IFirebaseDatadaseADO.IDataChange{
            override fun notifyDataChanged() {
                mRecyclerLessonAdapter = RecyclerLessonAdapter(mLessonADO.list,object :RecyclerLessonAdapter.OnItemClickListener{
                    override fun onItemClick(item: Lesson) {
                        val i = Intent(this@LessonsActivity,LessonMaintenanceActivity::class.java)
                        i.putExtra("userId",userId)
                        i.putExtra("lessonId",item.keyId)
                        startActivity(i)

                    }
                })
                recyclerView.adapter  = mRecyclerLessonAdapter
                mRecyclerLessonAdapter.notifyDataSetChanged()
            }

        })

    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
    }

}
