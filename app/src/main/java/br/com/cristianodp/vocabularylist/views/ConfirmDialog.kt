package br.com.cristianodp.vocabularylist.views

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

/**
 * Created by crist on 23/02/2018.
 */
class ConfirmDialog(context:Context,title:String,message:String,listener: IListenner): AlertDialog.Builder(context) {

    init {

        this.setTitle(title)
        this.setMessage(message)
        this.setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    listener.onYes()
                })

        this.setNegativeButton(android.R.string.no, DialogInterface.OnClickListener { dialog, which ->
            listener.onNo()
        })
        this.setCancelable(false)
        this.show()

    }

    interface IListenner {
        fun onYes()
        fun onNo()
    }
}