package br.com.cristianodp.vocabularylist.adapters

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ScrollingTabContainerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.models.CollectionLesson
import br.com.cristianodp.vocabularylist.models.Lesson
import br.com.cristianodp.vocabularylist.views.CollectionActivity

/**
 * Created by crist on 16/01/2018.
 */
class RecyclerCollectionAdapter(var data:List<CollectionLesson>, var listener:OnItemClickListener) : RecyclerView.Adapter<RecyclerCollectionAdapter.ViewHolder>() {

    lateinit var context: Context

    init {
        Log.v("e","eeee")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var cell: View? = null
        if (parent != null) {
            this.context = parent.context
            var inflater = LayoutInflater.from(parent.context)
            cell = inflater.inflate(R.layout.collection_item_layout,parent,false)
        }
        return ViewHolder(cell)
    }

 ///   @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null){
            var item = data[position]
            holder.imageViewColor.setBackgroundColor(context.resources.getColor(R.color.colorBackSide))
            holder.textViewTitle.text = item.name
            holder.textViewSubTitle.text = item.detail
            holder.textViewTotal.setText(item.totLessons.toString())
            holder.progressBar.progress = item.percDone
            holder.imageButtonAction.visibility = View.GONE
            holder.imageButtonAction.setOnClickListener {

            }
            holder.viewRow.setOnLongClickListener(object :View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                   // holder.imageButtonAction.visibility = View.VISIBLE
                    return true
                }
            })

            holder.viewRow.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    val i = Intent(context,CollectionActivity::class.java)
                    i.putExtra("userId",item.authorId)
                    i.putExtra("collectionId",item.keyId)
                    i.putExtra("isFormEditable",false)
                    context.startActivity(i)
                }

            })
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }


    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var imageViewColor: ImageView
        lateinit var textViewTitle : TextView
        lateinit var textViewSubTitle : TextView
        lateinit var textViewTotal: TextView
        lateinit var imageButtonAction: ImageButton
        lateinit var progressBar: ProgressBar
        lateinit var viewRow: ConstraintLayout

        constructor(itemView: View?) : super(itemView) {
            if (itemView != null){
                this.imageViewColor = itemView.findViewById(R.id.imageViewColor)
                this.textViewTitle = itemView.findViewById(R.id.textViewTitle)
                this.textViewSubTitle = itemView.findViewById(R.id.textViewSubTitle)
                this.textViewTotal = itemView.findViewById(R.id.textViewTotal)
                this.imageButtonAction = itemView.findViewById(R.id.imageButtonAction)
                this.progressBar = itemView.findViewById(R.id.progressBar)
                this.viewRow = itemView.findViewById(R.id.containerView)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item:Lesson)
    }

}