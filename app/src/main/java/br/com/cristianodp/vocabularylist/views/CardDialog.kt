package br.com.cristianodp.vocabularylist.views

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.TabLayout
import android.view.View
import android.view.Window
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.ado.CardADO
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatabaseADO
import br.com.cristianodp.vocabularylist.models.Card
import kotlinx.android.synthetic.main.dialog_card_layout.*

/**
 * Created by crist on 18/02/2018.
 */
class CardDialog(context: Context,var path:String, var card: Card):Dialog(context) {

    private lateinit var mCardADO:CardADO

    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.dialog_card_layout)
        val width = (context.resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (context.resources.displayMetrics.heightPixels * 0.90).toInt()
        this.window.setLayout(width,height)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mCardADO = CardADO(path,IFirebaseDatabaseADO.TypeListner.SINGLE,object :IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                mCardADO.getValue()?.let {
                    card = it
                    editTextFront.setText(it.word)
                    editTextBack.setText(it.definition)
                }
            }
        })

        fabSave.setOnClickListener {
            card.word = editTextFront.text.toString()
            card.definition =editTextBack.text.toString()
            mCardADO.push(card)
            this.dismiss()
        }

        fabCancel.setOnClickListener {
            this.dismiss()
        }

        setPositionTabs(0)

        tablayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {

                    setPositionTabs(tab.position)
                }

            }

        })




        this.show()
    }


    fun setPositionTabs(position:Int){
        when (position){
            0 -> {
                cardView1.visibility = View.VISIBLE
                editTextFront.requestFocus()
                cardView2.visibility = View.GONE

            }
            1 -> {
                cardView1.visibility = View.GONE
                editTextBack.requestFocus()
                cardView2.visibility = View.VISIBLE

            }
        }
    }

}