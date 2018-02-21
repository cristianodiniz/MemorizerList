package br.com.cristianodp.vocabularylist.models


/**
 * Created by crist on 06/02/2018.
 */
class Lesson(var keyId:String,var description:String, var cards:Int,var collectionId: String) {

    constructor():this("","",0,"")

    constructor(keyId:String):this(keyId,"",0,"")

    constructor(keyId:String, collectionId: String):this(keyId,"",0,collectionId)

}

