package com.dheeraj.realm.data.model

import io.realm.RealmObject

open class Student(
    var name: String?= null,
    var age: Int?= null
) : RealmObject(){}