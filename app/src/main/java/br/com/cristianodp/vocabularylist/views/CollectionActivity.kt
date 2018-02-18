package br.com.cristianodp.vocabularylist.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.cristianodp.vocabularylist.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class CollectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        setSupportActionBar(toolbar)
    }
}
