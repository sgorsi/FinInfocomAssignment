package com.example.fininfocomassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fininfocomassignment.model.UserData
import io.realm.Realm
import io.realm.Sort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {

    private val realm = Realm.getDefaultInstance()

    private val _userLiveData = MutableLiveData<List<UserData>>()
    val userLiveData: LiveData<List<UserData>> = _userLiveData

    init {

        insertUserData()

    }




private fun insertUserData() {

    Realm.getDefaultInstance().executeTransaction { realm ->
        realm.insertOrUpdate(
            listOf(
                UserData().apply {
                    id = 1
                    name = "Ashok"
                    city = "bangalore"
                    age = 32
                    zip = 11002
                },
                UserData().apply {
                    id = 2
                    name = "Naveen"
                    city = "Delhi"
                    age = 29
                    zip = 11003
                },
                UserData().apply {
                    id = 3
                    name = "George"
                    city = "Kerala"
                    age = 24
                    zip = 11004
                }

            )
        )

    }
}

fun fetchUser(sortBy: String) {
    val allUser = realm.where(UserData::class.java)
        .findAll()
        .sort(sortBy, Sort.ASCENDING)
    _userLiveData.value = realm.copyFromRealm(allUser)

}



override fun onCleared() {
    super.onCleared()
    realm.close()
}

}