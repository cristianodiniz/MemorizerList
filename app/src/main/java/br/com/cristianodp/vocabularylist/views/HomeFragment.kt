package br.com.cristianodp.vocabularylist.views


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.adapters.RecyclerCollectionAdapter
import br.com.cristianodp.vocabularylist.ado.CollectionADO
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatabaseADO
import br.com.cristianodp.vocabularylist.global.getPathCollections
import br.com.cristianodp.vocabularylist.models.Lesson
import kotlinx.android.synthetic.main.fragment_home_layout.*

/**
 * Created by crist on 18/02/2018.
 */


class HomeFragment : Fragment() {

    private lateinit var userId:String
    private lateinit var mCollectionADO: CollectionADO
    private var mRecyclerCollectionAdapter: RecyclerCollectionAdapter? = null
    private val TAG = "HomeFragment"

    fun newInstance(userId: String): HomeFragment {
        val bundle = Bundle()
        bundle.putString("userId", userId)
        val fragment = HomeFragment()
        fragment.setArguments(bundle)
        return fragment
    }

    private fun readBundle(bundle: Bundle?) {
        if (bundle != null) {
            userId = bundle.getString("userId")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        readBundle(arguments)
        return inflater.inflate(R.layout.fragment_home_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListenners(context)
    }

    private fun initListenners(context: Context?) {

        mCollectionADO = CollectionADO(getPathCollections(userId),IFirebaseDatabaseADO.TypeListner.MULTIPLE,object:IFirebaseDatabaseADO.IDataChange{
            override fun notifyDataChanged() {
                try {


                if (recyclerViewHome.layoutManager == null){
                    recyclerViewHome.layoutManager =  LinearLayoutManager(context)
                }

                if (mRecyclerCollectionAdapter != null) {
                    mRecyclerCollectionAdapter!!.data = mCollectionADO.list
                    mRecyclerCollectionAdapter!!.notifyDataSetChanged()
                }else{
                    mRecyclerCollectionAdapter = RecyclerCollectionAdapter(mCollectionADO.list,object : RecyclerCollectionAdapter.OnItemClickListener{
                        override fun onItemClick(item: Lesson) {
                            val i = Intent(activity,LessonMaintenanceActivity::class.java)
                            i.putExtra("userId",userId)
                            i.putExtra("collectionId",item.collectionId)
                            i.putExtra("lessonId",item.keyId)
                            startActivity(i)
                        }
                    })
                    recyclerViewHome.adapter  = mRecyclerCollectionAdapter
                }
                }catch (e:Exception){
                    Log.e(TAG,e.toString())
                }
            }
        })
    }
}