package br.com.cristianodp.vocabularylist.views

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.models.Card
import kotlinx.android.synthetic.main.card_item_layout.*
import java.util.*


class CardsFragment : Fragment() {


    private lateinit var mCard:Card
    private val TAG = "CardsFragment"
    private var side = 0
    private lateinit var textToSpeech: TextToSpeech
    //lateinit var cxt:Context

    init {
        side = 0
        Log.v(TAG,"init")



    }

    fun newInstance(card:Card): CardsFragment {
        val bundle = Bundle()
        bundle.putSerializable("Card", card)
        val fragment = CardsFragment()
       // fragment.cxt = context
        fragment.arguments = bundle
        return fragment
    }

    private fun readBundle(bundle: Bundle?) {
        if (bundle != null) {
            mCard = bundle.getSerializable("Card") as Card
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        readBundle(arguments)
        return inflater.inflate(R.layout.card_item_layout,container,false)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cardView.setOnClickListener {
            loadFields()
        }

        imageButtonSpeech.setOnClickListener {

            val text = textViewTitle.text.toString()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }

        textToSpeech = TextToSpeech(context!!, TextToSpeech.OnInitListener { status ->
            if(status != TextToSpeech.ERROR){
                textToSpeech.language = Locale.UK
                textToSpeech.setSpeechRate(0.5f)
            }
        })

        loadFields()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun loadFields(){
        if (side == 0){
            textViewTitle.text = mCard.word
            cardContainer.setBackgroundColor(resources.getColor(R.color.colorFrontSide, context!!.theme))
            side = 1
        }else{
            textViewTitle.text = mCard.definition
            cardContainer.setBackgroundColor(resources.getColor(R.color.colorBackSide, context!!.theme))
            side = 0
        }
    }
}
