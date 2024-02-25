package com.example.fininfocomassignment.model

sealed class ResponseData{
    data class SuccessMessage(val successMessage: String?): ResponseData()
    data class ErrorMessage(val errorMessage : String):ResponseData()
}