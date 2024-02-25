package com.example.fininfocomassignment.viewmodel
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fininfocomassignment.model.ResponseData
import com.example.fininfocomassignment.utility.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginViewModel : ViewModel() {
    private val _loginResultLiveData = MutableLiveData<ResponseData>()
    val loginResultLiveData:LiveData<ResponseData> = _loginResultLiveData
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.getReference("users")

    fun signIn(username: String, password: String) {
        val loginResult = validateInput(username, password)
        if (loginResult is ResponseData.SuccessMessage) {
            val userReference = databaseReference.orderByChild(username)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d(TAG, "DataSnapshot = $dataSnapshot")
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val storedPassword =
                                userSnapshot.child("password").getValue(String::class.java)
                            if (storedPassword == password) {
                                _loginResultLiveData.value =
                                    ResponseData.SuccessMessage("Login Success")
                            } else {
                                _loginResultLiveData.value =
                                    ResponseData.ErrorMessage("Username or Password is Incorrect")
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _loginResultLiveData.value = ResponseData.ErrorMessage(error.message)
                }
            })
        }

        else {
            _loginResultLiveData.value = loginResult
        }
    }

    private fun validateInput(username: String, password: String): ResponseData {

        if(username.isEmpty())
        {
            return  ResponseData.ErrorMessage("Please Enter Username")
        }
        if (!username.matches(Constants.userNamePattern))
        {

            return ResponseData.ErrorMessage("username must be of 10 characters")
        }
        if (!password.matches(Constants.passwordPattern))
        {

            return ResponseData.ErrorMessage("Password must be 7 Characters with 1 UpperCase Alphabet and 1SpecialCharacter and Numeric")
        }

        return ResponseData.SuccessMessage(null)

    }
}


