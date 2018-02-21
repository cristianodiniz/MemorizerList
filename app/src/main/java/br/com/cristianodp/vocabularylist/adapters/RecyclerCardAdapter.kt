package br.com.cristianodp.vocabularylist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.speech.tts.TextToSpeech
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ScrollingTabContainerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.models.Card
import br.com.cristianodp.vocabularylist.models.Lesson
import java.util.*

/**
 * Created by crist on 16/01/2018.
 */
class RecyclerCardAdapter(var context: Context,var data:List<Card>, var listener:OnItemClickListener) : RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder>() {

    var side:Int
    var isEditable:Boolean = false
    //speech
    private lateinit var textToSpeech: TextToSpeech
    init {
        side = 0
        Log.v("e","eeee")

        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if(status != TextToSpeech.ERROR){
                textToSpeech.language = Locale.UK
                textToSpeech.setSpeechRate(0.5f)
            }
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var cell: View? = null
        if (parent != null) {
            this.context = parent.context
            var inflater = LayoutInflater.from(parent.context)
            cell = inflater.inflate(R.layout.card_item_layout,parent,false)
        }
        return ViewHolder(cell)
    }

 ///   @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null){
            side = 0
            holder.textViewTitle.text = data[position].word
            holder.cardContainer.setBackgroundColor(context.resources.getColor(R.color.colorFrontSide))

            holder.imageButtonSpeech.setOnClickListener {
                /*var text = data.get(position).word
                if (side == 1) {
                    text = data.get(position).definition
                }*/
                var text = holder.textViewTitle.text.toString()
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }

            holder.cardContainer.setOnClickListener {
                if (side == 0){
                    holder.textViewTitle.text = data[position].word
                    holder.cardContainer.setBackgroundColor(context.resources.getColor(R.color.colorFrontSide))
                    side = 1
                }else{
                    holder.textViewTitle.text = data[position].definition
                    holder.cardContainer.setBackgroundColor(context.resources.getColor(R.color.colorBackSide))
                    side = 0
                }
            }

            holder.cardContainer.setOnLongClickListener {
                listener.onItemClick(data[position])
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }


    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var cardContainer : ConstraintLayout
        lateinit var textViewTitle : TextView
        lateinit var imageButtonSpeech:ImageButton

        constructor(itemView: View?) : super(itemView) {
            if (itemView != null){
                this.textViewTitle = itemView.findViewById(R.id.textViewTitle)
                this.cardContainer = itemView.findViewById(R.id.cardContainer)
                this.imageButtonSpeech = itemView.findViewById(R.id.imageButtonSpeech)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item:Card)
    }

}