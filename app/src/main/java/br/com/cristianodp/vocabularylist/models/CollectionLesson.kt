package br.com.cristianodp.vocabularylist.models

/**
 * Created by crist on 18/02/2018.
 */
class CollectionLesson(var keyId:String,
                       var name:String,
                       var detail:String,
                       var totLessons:Int,
                       var percDone:Int,
                       var public:Boolean,
                       var authorId:String) {
    constructor():this("","","",0,0,false,"")
    constructor(keyId: String, authorId: String):this(keyId,"","",0,0,false,authorId)

    fun isValid():Boolean{
        return true
    }
}