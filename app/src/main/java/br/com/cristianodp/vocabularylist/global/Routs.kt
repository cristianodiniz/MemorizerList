package br.com.cristianodp.vocabularylist.global

/**
 * Created by crist on 06/02/2018.
 */

fun getPathCards(userId:String,lessonKeyId:String):String{
    return "profiles/"+userId+"/cards/"+lessonKeyId
}


fun getPathCard(userId: String, lessonKeyId: String,cardKeyId: String):String{
    return "profiles/"+userId+"/cards/"+lessonKeyId+"/"+cardKeyId
}

fun getPathLessons(userId: String):String{
    return "profiles/"+userId+"/lessons"
}

fun getPathLesson(userId: String, lessonKeyId: String):String{
    return "profiles/"+userId+"/lessons/"+lessonKeyId
}