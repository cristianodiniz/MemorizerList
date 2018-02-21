package br.com.cristianodp.vocabularylist.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.ado.CardADO
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatabaseADO
import br.com.cristianodp.vocabularylist.global.getPathCard
import br.com.cristianodp.vocabularylist.models.Card
import kotlinx.android.synthetic.main.activity_card.*

class CardActivity : AppCompatActivity() {

    private lateinit var userId:String
    private lateinit var lessonId:String
    private lateinit var collectionId:String
    private lateinit var cardId:String

    private lateinit var mCardADO:CardADO
    private lateinit var mCard:Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userId = intent.getStringExtra("userId")
        collectionId = intent.getStringExtra("collectionId")
        lessonId = intent.getStringExtra("lessonId")
        cardId = intent.getStringExtra("cardId")
        mCard = Card(cardId,lessonId)

        fabDoneFront.hide()
        fabDoneBack.hide()

        intListners()

    }

    private fun intListners() {
        mCardADO = CardADO(getPathCard(userId,collectionId,lessonId,cardId),IFirebaseDatabaseADO.TypeListner.SINGLE,object : IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                if (mCardADO.getValue() != null){
                    mCard = mCardADO.getValue()!!
                }
                editTextFront.setText(mCard.word)
                editTextBack.setText(mCard.definition)
            }
        })


        editTextFront.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( mCard.word != editTextFront.text.toString()){
                    fabDoneFront.show()
                }else{
                    fabDoneFront.hide()
                }
            }
        })

        editTextBack.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( mCard.definition != editTextBack.text.toString()){
                    fabDoneBack.show()
                }else{
                    fabDoneBack.hide()
                }
            }
        })


        fabDoneFront.setOnClickListener {
            mCard.word = editTextFront.text.toString()
            mCardADO.push(mCard)
        }

        fabDoneBack.setOnClickListener {
            mCard.definition = editTextBack.text.toString()
            mCardADO.push(mCard)
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

