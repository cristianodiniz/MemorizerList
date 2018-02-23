package br.com.cristianodp.vocabularylist.global

/**
 * Created by crist on 06/02/2018.
 */


fun getPathCollections(userId: String):String{
    return "profiles/$userId/collections"
}


fun getPathCollectionsLibrary():String{
    return "profiles/public"
}


fun getPathCollection(userId: String, collectionKeyId: String):String{
    val path = getPathCollections(userId)

    return "$path/$collectionKeyId"
}

fun getPathLessons(userId: String, collectionKeyId: String):String{
    val path = getPathCollections(userId)
    return "$path/$collectionKeyId/lessons"
}

fun getPathLesson(userId: String, collectionKeyId: String, lessonKeyId: String):String{
    val path = getPathLessons(userId,collectionKeyId)
    return "$path/$lessonKeyId"
}

fun getPathCards(userId:String, collectionKeyId: String,lessonKeyId:String):String{
    val path = getPathLessons(userId,collectionKeyId)
    return "$path/$lessonKeyId/cards"
}


fun getPathCard(userId: String, collectionKeyId: String, lessonKeyId: String,cardKeyId: String):String{
    val path = getPathCards(userId,collectionKeyId,lessonKeyId)
    return "$path/$cardKeyId"
}


