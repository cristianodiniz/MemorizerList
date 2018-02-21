package br.com.cristianodp.vocabularylist.ado

import android.util.Log
import br.com.cristianodp.vocabularylist.models.Lesson
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by crist on 06/02/2018.
 */
class LessonADO(override var path: String, override var typeListner: IFirebaseDatabaseADO.TypeListner, override var dataChange: IFirebaseDatabaseADO.IDataChange): IFirebaseDatabaseADO<Lesson>{

    var list: ArrayList<Lesson>
    init{
        list = ArrayList<Lesson>()
        if (typeListner == IFirebaseDatabaseADO.TypeListner.MULTIPLE){
            initDatabaseChildListener()
        }else{
            initDatabaseValueListener()
        }
    }

    override fun removeList(item: Lesson?) {
        if (item != null) {
            val oldMov = list.filter { it-> it.keyId == item.keyId}[0]
            list.remove(oldMov)
        }
    }

    override fun updateList(item: Lesson?) {
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

    override fun processSnapshot(snapshot: DataSnapshot?): Lesson? {
        if (snapshot != null) {
            try {
                var value = snapshot.getValue(Lesson::class.java)
                if (value != null) {
                    return value
                }
            }catch (e:Exception){
                Log.e("processSnapshot",e.toString())
            }

        }
        return null

    }

    override fun push(item: Lesson): Boolean {
        try {

            val mFirebaseDatabase = FirebaseDatabase.getInstance()
            val mMovtoDatabaseReference = mFirebaseDatabase.getReference(path)
            if (item.keyId == ""){
                item.keyId = mMovtoDatabaseReference.push().key
            }
            mMovtoDatabaseReference.setValue(item)

            return true
        }catch (e:Exception){
            Log.e("LessonADO->push",e.toString())
            return false
        }
    }

    override fun getValue():Lesson?{
        if (list.size > 0){
            return list[0]
        }

        return null
    }
}