package br.com.cristianodp.vocabularylist.models

/**
 * Created by crist on 18/02/2018.
 */
class Classification(var keyId:String,var name:String,var detail:String,var public:String,var author:String) {
    constructor():this("","","","","")
    constructor(keyId: String):this(keyId,"","","","")

    fun isValid():Boolean{
        return true
    }
}