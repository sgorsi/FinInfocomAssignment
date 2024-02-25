package com.example.fininfocomassignment.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserData: RealmObject() {
    @PrimaryKey var id: Long = 0
    var name:String = ""
    var city:String = ""
    var age:Int = 0
    var zip:Int = 0
}