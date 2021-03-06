package br.com.cristianodp.vocabularylist.ado


import android.os.Build
import android.support.annotation.RequiresApi
import com.google.firebase.database.*

/**
 * Created by crist on 25/01/2018.
 */

interface IFirebaseDatabaseADO<T>{
    var path:String
    var typeListner:TypeListner
    var dataChange:IDataChange
    fun initDatabaseChildListener() {

        var mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseDatabase.getReference(path).addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                dataChange.notifyDataChanged()
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                dataChange.notifyDataChanged()
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onChildChanged(snapshot: DataSnapshot?, p1: String?) {

                val item = processSnapshot(snapshot)
                updateList(item)
                dataChange.notifyDataChanged()


            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {

                val item = processSnapshot(snapshot)
                updateList(item)
                dataChange.notifyDataChanged()

            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onChildRemoved(snapshot: DataSnapshot?) {

                val item = processSnapshot(snapshot)
                removeList(item)
                dataChange.notifyDataChanged()

            }
        })
    }

    fun initDatabaseValueListener() {

        var mFirebaseDatabase = FirebaseDatabase.getInstance()

        mFirebaseDatabase.getReference(path).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError?) {
                dataChange.notifyDataChanged()
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                val item = processSnapshot(snapshot)
                updateList(item)
                dataChange.notifyDataChanged()

            }
        })

    }

    fun removeList(item: T?)

    fun updateList(item: T?)

    fun push(item: T):Boolean

    //fun erase():Boolean
    fun erase(): Boolean {
        val mFirebaseDatabase = FirebaseDatabase.getInstance()
        val mDatabaseReference = mFirebaseDatabase.getReference(path)

        mDatabaseReference.removeValue()

        return true
    }

    fun getValue():T?

    fun processSnapshot(snapshot: DataSnapshot?):T?

    interface IDataChange {
        fun notifyDataChanged()
    }

    enum class TypeListner {
        SINGLE, MULTIPLE
    }

}

fun generateFirebaseId():String{
    return FirebaseDatabase.getInstance().reference.push().key
}

