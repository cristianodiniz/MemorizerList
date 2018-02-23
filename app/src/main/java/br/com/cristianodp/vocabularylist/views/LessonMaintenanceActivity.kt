package br.com.cristianodp.vocabularylist.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.adapters.RecyclerCardAdapter
import br.com.cristianodp.vocabularylist.ado.CardADO
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatabaseADO
import br.com.cristianodp.vocabularylist.ado.LessonADO
import br.com.cristianodp.vocabularylist.ado.generateFirebaseId
import br.com.cristianodp.vocabularylist.global.getPathCard
import br.com.cristianodp.vocabularylist.global.getPathCards
import br.com.cristianodp.vocabularylist.global.getPathLesson
import br.com.cristianodp.vocabularylist.models.Card
import br.com.cristianodp.vocabularylist.models.Lesson
import kotlinx.android.synthetic.main.activity_lesson_maintenance.*

class LessonMaintenanceActivity : AppCompatActivity() {

    private lateinit var userId:String
    private lateinit var collectionId:String
    private lateinit var lessonId:String
    private lateinit var mLessonADO:LessonADO
    private lateinit var mLesson:Lesson
    private lateinit var mCardADO:CardADO
    private lateinit var mRecyclerCardAdapter:RecyclerCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_maintenance)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userId = intent.getStringExtra("userId")
        collectionId = intent.getStringExtra("collectionId")
        lessonId = intent.getStringExtra("lessonId")
        mLesson = Lesson(lessonId,collectionId)

        intListners()
        showButtonsEditDescription(false)
    }

    private fun intListners() {
        mLessonADO = LessonADO(getPathLesson(userId,collectionId,lessonId),IFirebaseDatabaseADO.TypeListner.SINGLE,object: IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                if (mLessonADO.getValue() != null){
                    mLesson.description = mLessonADO.getValue()!!.description
                }
                editTextDescription.setText(mLesson.description)
            }
        })

        //recyclerView.layoutManager = LinearLayoutManager(this@LessonMaintenanceActivity)
        recyclerView.layoutManager = GridLayoutManager(this@LessonMaintenanceActivity, 2)
        mCardADO = CardADO(getPathCards(userId,collectionId,lessonId),IFirebaseDatabaseADO.TypeListner.MULTIPLE,object : IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                mRecyclerCardAdapter = RecyclerCardAdapter(this@LessonMaintenanceActivity,mCardADO.list,object : RecyclerCardAdapter.OnItemClickListener{
                    override fun onItemClick(item: Card) {

                        CardDialog(this@LessonMaintenanceActivity
                                ,getPathCard(userId,collectionId,item.lessonId, item.keyId)
                                ,item)

                    }
                })
                recyclerView.adapter  = mRecyclerCardAdapter
                mRecyclerCardAdapter.notifyDataSetChanged()
            }

        })

        editTextDescription.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( mLesson.description != editTextDescription.text.toString()){
                    showButtonsEditDescription(true)
                }else{
                    showButtonsEditDescription(false)
                }

            }
        })

        floatingActionButtonDone.setOnClickListener {
            mLesson.description = editTextDescription.text.toString()
            mLessonADO.push(mLesson)


        }

        floatingActionButtonCanc.setOnClickListener {
            editTextDescription.setText(mLesson.description)
        }

        floatingActionButtonAdd.setOnClickListener {
            var keyId = generateFirebaseId()
            CardDialog(this@LessonMaintenanceActivity
                    ,getPathCard(userId,collectionId,lessonId, keyId)
                    ,Card(keyId,lessonId))

        }

    }

    fun showButtonsEditDescription(show: Boolean){
        if (!show){
            floatingActionButtonDone.hide()
            floatingActionButtonCanc.hide()
        }else{
            floatingActionButtonDone.show()
            floatingActionButtonCanc.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }



}
