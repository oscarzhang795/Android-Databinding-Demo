package com.example.oscarzhang.databindingdemo

import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

class User : Observable {

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mPropertyChangedRegistry.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mPropertyChangedRegistry.add(callback)
    }

//    val firstname: ObservableField<String> = ObservableField()

    private val mPropertyChangedRegistry = PropertyChangeRegistry()

    @get:Bindable
    var firstname: String = ""
        set(value) {
            field = value
            mPropertyChangedRegistry.notifyChange(this, BR.firstname)
        }

    @get:Bindable
    var lastname: String = ""
        set(value) {
            field = value
            mPropertyChangedRegistry.notifyChange(this, BR.lastname)
        }




    constructor(firstname: String, lastname: String) {
        this.firstname = firstname
//        this.firstname.set(firstname)
        this.lastname = lastname
    }
//
//    @get:Bindable
//    var timeRecordStatus = TimeRecordStatus.OFFLINE.toString()
//        set(timeRecordStatus) {
//            field = timeRecordStatus
//            mPropertyChangeRegistry.notifyChange(this, BR.timeRecordStatus)
//        }


}