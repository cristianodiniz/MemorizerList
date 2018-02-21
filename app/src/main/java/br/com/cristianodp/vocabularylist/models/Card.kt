package br.com.cristianodp.vocabularylist.models

import java.io.Serializable

/**
 * Created by crist on 16/01/2018.
 */


class Card(var keyId:String,var lessonId:String,var word:String,var definition:String):Serializable{
    //constructor(lessonId:String,word:String, definition:String):this("",lessonId,word,definition)
    constructor(keyId:String,lessonId:String):this(keyId,lessonId,"","")
    constructor():this("","","","")
    fun isValid():Boolean{
        if (this.keyId.isEmpty()){
            return false
        }
        if (this.lessonId.isEmpty()){
            return false
        }

        return true
    }
}