package com.example.fininfocomassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fininfocomassignment.databinding.ActivityLoginBinding
import com.example.fininfocomassignment.model.ResponseData
import com.example.fininfocomassignment.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setUpObservers()
        setUpListener()
    }

    private fun setUpListener() {
        binding.apply {

            btnLogin.setOnClickListener {
                
                btnLogin.visibility = View.GONE
                progressCircular.visibility = View.VISIBLE
                val userName = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

                viewModel.signIn(userName, password)
            }


            tvCreateNewAcc.setOnClickListener {

                startActivity(Intent(this@LoginActivity,SignupActivity::class.java))
            }

        }



    }

    private fun setUpObservers() {

        viewModel.loginResultLiveData.observe(this){loginResult ->

            when(loginResult){

                is ResponseData.SuccessMessage ->
                {

                    showToast(loginResult.successMessage.toString())

                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    finish()
                }
                is ResponseData.ErrorMessage ->
                {

                 showToast(loginResult.errorMessage)

                }
            }


            binding.apply {
                btnLogin.visibility = View.VISIBLE
                progressCircular.visibility = View.GONE
            }
        }


    }


    private fun showToast(toastMessage: String) {

        Toast.makeText(this@LoginActivity, toastMessage, Toast.LENGTH_SHORT).show()
    }
}