package com.dheeraj.realm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dheeraj.realm.R
import com.dheeraj.realm.data.model.Student
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        //added to delete the db once the activity is created. Only use this if required
        realm.deleteAll()
        realm.commitTransaction()

        btn_save.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        realm.executeTransactionAsync ({
            val student = it.createObject(Student::class.java)
            student.name = edittextName.text.toString()
            student.age = edittextAge.text.toString().toInt()
        },{
            Log.d(TAG,"On Success: Data Written Successfully!")
            readData()
        },{
            Log.d(TAG,"On Error: Error in saving Data!")
        })
    }

    private fun readData() {
        val students = realm.where(Student::class.java).findAll()
        var response=""
        students.forEach {
            response = response + "Name: ${it.name}, Age: ${it.age}" +"\n"
        }
        db_response.text = response
    }
}
