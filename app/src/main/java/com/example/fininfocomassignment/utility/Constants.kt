package com.example.fininfocomassignment.utility

object Constants
{

      val userNamePattern = "^[a-zA-Z]{10}$".toRegex()
      val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$".toRegex()

}