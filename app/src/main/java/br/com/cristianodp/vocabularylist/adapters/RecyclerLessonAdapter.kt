package br.com.cristianodp.vocabularylist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ScrollingTabContainerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.models.Lesson

/**
 * Created by crist on 16/01/2018.
 */
class RecyclerLessonAdapter(var data:List<Lesson>, private var listener:OnItemClickListener) : RecyclerView.Adapter<RecyclerLessonAdapter.ViewHolder>() {

    lateinit var context: Context
    var isEditable:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var cell: View? = null
        if (parent != null) {
            this.context = parent.context
            var inflater = LayoutInflater.from(parent.context)
            cell = inflater.inflate(R.layout.lesson_item_layout,parent,false)
        }
        return ViewHolder(cell)
    }

 ///   @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null){
            holder.textViewTitle.text = data[position].description
         ///   holder.textViewSubTitle.text = "Words 0"
            holder.textViewSubTitle.text = data[position].cards.toString()
            holder.viewRow.setOnClickListener{
                listener.onItemClick(data[position])
            }
            if (isEditable) {
                holder.imageButtonPlay.setImageDrawable(context.resources.getDrawable(R.drawable.ic_edit_black_24dp,context.theme))
            }else{
                holder.imageButtonPlay.setImageDrawable(context.resources.getDrawable(android.R.drawable.ic_media_play,context.theme))
            }
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }


    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var textViewTitle : TextView
        lateinit var textViewSubTitle : TextView
        lateinit var imageButtonPlay: ImageView
        lateinit var viewRow: ConstraintLayout

        constructor(itemView: View?) : super(itemView) {
            if (itemView != null){
                this.textViewTitle = itemView.findViewById(R.id.textViewTitle)
                this.textViewSubTitle = itemView.findViewById(R.id.textViewSubTitle)
                this.imageButtonPlay = itemView.findViewById(R.id.imageButtonPlay)
                this.viewRow = itemView.findViewById(R.id.containerView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item:Lesson)
    }

}