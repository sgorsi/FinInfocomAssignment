package com.example.fininfocomassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fininfocomassignment.model.ResponseData
import com.example.fininfocomassignment.utility.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUpViewModel:ViewModel() {

    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.getReference("users")
    private val _registerLiveData =  MutableLiveData<ResponseData>()
    val registerLiveData:LiveData<ResponseData> = _registerLiveData


    fun signup(regUserName: String, regPassword: String, regConfirmPassword: String)
    {
        val validationResponse = validateData(regUserName,regPassword,regConfirmPassword)

        if (validationResponse is ResponseData.SuccessMessage)
        {
            databaseReference.orderByChild("userName").equalTo(regUserName).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists())
                    {
                        val id  = databaseReference.push().key

                        val userData = mapOf("userName" to regUserName, "password" to regPassword)
                        databaseReference.child(id!!).setValue(userData)

                        _registerLiveData.value =  ResponseData.SuccessMessage("Registered Successfully")
                    }else

                    {
                        _registerLiveData.value =  ResponseData.ErrorMessage("username is  already exist ")

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                    _registerLiveData.value =  ResponseData.ErrorMessage(error.message)

                }

            })

        }else
        {

            _registerLiveData.value = validationResponse

        }

    }

   private fun validateData(regUserName: String, regPassword: String, regConfirmPassword: String): ResponseData{
        if (!regUserName.matches(Constants.userNamePattern))
        {
            return ResponseData.ErrorMessage("Username must be of 10 characters")
        }
       if (regConfirmPassword != regPassword) {
           return ResponseData.ErrorMessage("Password does not match")
       }

       if(!regConfirmPassword.matches(Constants.passwordPattern))
       {
           return ResponseData.ErrorMessage("Password must be 7 Characters with 1 UpperCase Alphabet and 1SpecialCharacter and Numeric")
       }

       return  ResponseData.SuccessMessage(null)

    }


}