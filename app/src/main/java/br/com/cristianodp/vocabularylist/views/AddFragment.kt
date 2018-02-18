package br.com.cristianodp.vocabularylist.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cristianodp.vocabularylist.R

/**
 * Created by crist on 18/02/2018.
 */

class AddFragment(): Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add_layout,container,false)

    }
}