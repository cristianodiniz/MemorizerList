package br.com.cristianodp.vocabularylist.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.adapters.RecyclerLessonAdapter
import br.com.cristianodp.vocabularylist.ado.CollectionADO
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatabaseADO
import br.com.cristianodp.vocabularylist.ado.LessonADO
import br.com.cristianodp.vocabularylist.ado.generateFirebaseId
import br.com.cristianodp.vocabularylist.global.getPathCollection
import br.com.cristianodp.vocabularylist.global.getPathLessons
import br.com.cristianodp.vocabularylist.models.CollectionLesson
import br.com.cristianodp.vocabularylist.models.Lesson
import kotlinx.android.synthetic.main.activity_collection.*

import android.widget.Switch
import android.support.v4.view.MenuItemCompat



class CollectionActivity : AppCompatActivity() {

    private lateinit var userId:String
    private lateinit var collectionId:String
    private lateinit var mCollection: CollectionLesson
    private lateinit var mCollectionADO:CollectionADO
    private lateinit var mLessonADO:LessonADO
    private var mRecyclerLessonAdapter:RecyclerLessonAdapter? = null
    private var isFormEditable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userId = intent.getStringExtra("userId")
        collectionId = intent.getStringExtra("collectionId")
        isFormEditable = intent.getBooleanExtra("isFormEditable",false)
        setFormEditable(isFormEditable)
        mCollection = CollectionLesson(collectionId,userId)
        intListners()
    }

    override fun onResume() {
        super.onResume()
        fabSaveCollection.hide()
        fabCancCollection.hide()
        editTextTitle.clearFocus()
        toggleButtonPublic.requestFocus()

    }

    private fun intListners() {
        mCollectionADO = CollectionADO(getPathCollection(userId,collectionId), IFirebaseDatabaseADO.TypeListner.SINGLE,object:IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                mCollectionADO.getValue()?.let {
                    loadField(it)
                }
            }
        })
        recycleViewLessons.layoutManager = GridLayoutManager(this@CollectionActivity, 2)

        mLessonADO = LessonADO(getPathLessons(userId,collectionId),IFirebaseDatabaseADO.TypeListner.MULTIPLE,object:IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                if (mRecyclerLessonAdapter != null) {
                    mRecyclerLessonAdapter!!.data = mLessonADO.list
                    mRecyclerLessonAdapter!!.isEditable = isFormEditable
                    mRecyclerLessonAdapter!!.notifyDataSetChanged()
                }else{
                    mRecyclerLessonAdapter = RecyclerLessonAdapter(mLessonADO.list,object : RecyclerLessonAdapter.OnItemClickListener{
                        override fun onItemClick(item: Lesson) {
                            var i = Intent(this@CollectionActivity, LessonSlidePagerActivity::class.java)
                            if (isFormEditable) {
                                i = Intent(this@CollectionActivity, LessonMaintenanceActivity::class.java)
                            }
                            i.putExtra("userId", userId)
                            i.putExtra("collectionId", item.collectionId)
                            i.putExtra("lessonId", item.keyId)
                            startActivity(i)
                        }
                    })
                    mRecyclerLessonAdapter!!.isEditable = isFormEditable
                    recycleViewLessons.adapter  = mRecyclerLessonAdapter
                }

            }
        })
        fabSaveCollection.setOnClickListener {
           saveCollectionData()

            //finish()
        }

        fabCancCollection.setOnClickListener{
            editTextTitle.setText(mCollection.name)
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        }

        fabAddLesson.setOnClickListener {
            val i = Intent(this@CollectionActivity,LessonMaintenanceActivity::class.java)
            i.putExtra("userId",userId)
            i.putExtra("collectionId",collectionId)
            i.putExtra("lessonId", generateFirebaseId())
            startActivity(i)
        }

        editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( mCollection.name != editTextTitle.text.toString()){
                    fabSaveCollection.show()
                    fabCancCollection.show()
                }else{
                    fabSaveCollection.hide()
                    fabCancCollection.hide()
                }
            }
        })

        toggleButtonPublic.setOnCheckedChangeListener { buttonView, isChecked ->
            mCollection.public = isChecked
            saveCollectionData()
        }

    }

    private fun saveCollectionData() {
        mCollection.name = editTextTitle.text.toString()
        mCollection.public = toggleButtonPublic.isChecked
        mCollectionADO.push(mCollection)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private fun loadField(it: CollectionLesson) {
        mCollection.name = it.name
        mCollection.public = it.public
        editTextTitle.setText(it.name)
        toggleButtonPublic.isChecked = it.public
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.mn_stand_edit -> {

            }
            R.id.mn_stand_del -> {
                ConfirmDialog(this@CollectionActivity
                        ,resources.getString(R.string.dialog_confirm_del_title)
                        ,resources.getString(R.string.dialog_confirm_del_msg)
                        ,object :ConfirmDialog.IListenner{
                    override fun onYes() {
                        mCollectionADO.erase()
                        this@CollectionActivity.finish()
                    }

                    override fun onNo() {

                    }

                })
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_standar, menu)
        val view = MenuItemCompat.getActionView(menu.findItem(R.id.mn_stand_edit))
        val switcha = view.findViewById<Switch>(R.id.mn_stand_switch_edit)
        switcha.setOnCheckedChangeListener { buttonView, isChecked ->
            setFormEditable(isChecked)
        }
        switcha.isChecked = isFormEditable

        return true
    }

    fun setFormEditable(isEditable:Boolean){
        if (isEditable){
            fabAddLesson.show()
            toggleButtonPublic.isEnabled = true
            editTextTitle.isEnabled = true
        }else{
            fabSaveCollection.hide()
            fabAddLesson.hide()
            fabCancCollection.hide()
            toggleButtonPublic.isEnabled = false
            editTextTitle.isEnabled = false
        }
        mRecyclerLessonAdapter?.let {
            it.isEditable = isEditable
            it.notifyDataSetChanged()
        }

        isFormEditable = isEditable
    }

}
