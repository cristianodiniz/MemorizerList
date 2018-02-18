package br.com.cristianodp.vocabularylist.ado

import android.os.Build
import android.support.annotation.RequiresApi
import br.com.cristianodp.vocabularylist.models.Card
import br.com.cristianodp.vocabularylist.models.Lesson
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

/**
 * Created by crist on 06/02/2018.
 */
class CardADO(override var path: String, override var typeListner: String, override var dataChange: IFirebaseDatadaseADO.IDataChange) :IFirebaseDatadaseADO<Card> {

    var list: ArrayList<Card>
    init{
        list = ArrayList<Card>()
        if (typeListner == "CHILD"){
            initDatabaseChildListener()
        }else{
            initDatabaseValueListener()
        }
    }

    override fun removeList(item: Card?) {
        if (item != null) {
            val oldMov = list.filter { it-> it.keyId == item.keyId}[0]
            list.remove(oldMov)
        }
    }

    override fun updateList(item: Card?) {
        if (item != null) {
            try {
                val oldMov = list.filter { it-> it.keyId == item.keyId}[0]
                val idx = list.indexOf(oldMov)
                list[idx] = item
            }catch (e:Exception){
                list.add(item)
            }
        }
    }

    override fun processSnapshot(snapshot: DataSnapshot?): Card? {
        if (snapshot != null) {
            snapshot.getValue(Card::class.java)?.let{item ->
                return item
            }
        }
        return null

    }

    override fun push(item: Card): Boolean {
        if (item.isValid()){
            val mFirebaseDatabase = FirebaseDatabase.getInstance()
            val mMovtoDatabaseReference = mFirebaseDatabase.getReference(path)
            if (item.keyId == ""){
                item.keyId = mMovtoDatabaseReference.push().key
            }
            mMovtoDatabaseReference.setValue(item)
            return true
        }else{
            //Toast.makeText(this,"Preencha todos os campos", Toast.LENGTH_LONG).show()
            return false
        }
    }

    override fun genereteId():String {
        return FirebaseDatabase.getInstance().reference.push().key
    }


    override fun getValue(): Card? {
        if (list.size > 0){
            return list[0]
        }
        return null
    }



}