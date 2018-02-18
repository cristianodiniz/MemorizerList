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
import android.widget.TextView
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.models.Lesson

/**
 * Created by crist on 16/01/2018.
 */
class RecyclerLessonAdapter(var data:List<Lesson>,var listener:OnItemClickListener) : RecyclerView.Adapter<RecyclerLessonAdapter.ViewHolder>() {

    lateinit var context: Context

    init {
        Log.v("e","eeee")
    }

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
            holder.textViewTitle.setText(data.get(position).description)
         ///   holder.textViewSubTitle.text = "Words 0"
            holder.textViewSubTitle.setText(data.get(position).cards.toString())
            holder.viewRow.setOnClickListener{
                listener.onItemClick(data.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }


    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var textViewTitle : TextView
        lateinit var textViewSubTitle : TextView
        lateinit var viewRow: ConstraintLayout

        constructor(itemView: View?) : super(itemView) {
            if (itemView != null){
                this.textViewTitle = itemView.findViewById(R.id.textViewTitle)
                this.textViewSubTitle = itemView.findViewById(R.id.textViewSubTitle)
                this.viewRow = itemView.findViewById(R.id.containerView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item:Lesson)
    }

}