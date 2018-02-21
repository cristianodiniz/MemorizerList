package br.com.cristianodp.vocabularylist.global

/**
 * Created by crist on 06/02/2018.
 */

fun getPathCards(userId:String, collectionKeyId: String,lessonKeyId:String):String{
    return "profiles/"+userId+"/cards/"+collectionKeyId+"/"+lessonKeyId
}


fun getPathCard(userId: String, collectionKeyId: String, lessonKeyId: String,cardKeyId: String):String{
    return "profiles/"+userId+"/cards/"+collectionKeyId+"/"+lessonKeyId+"/"+cardKeyId
}

fun getPathLessons(userId: String, collectionKeyId: String):String{
    return "profiles/"+userId+"/lessons/"+collectionKeyId
}

fun getPathLesson(userId: String, collectionKeyId: String, lessonKeyId: String):String{
    return "profiles/"+userId+"/lessons/"+collectionKeyId+"/"+lessonKeyId
}

fun getPathCollections(userId: String):String{
    return "profiles/"+userId+"/collection"
}

fun getPathCollection(userId: String, collectionKeyId: String):String{
    return "profiles/"+userId+"/collection/"+collectionKeyId
}