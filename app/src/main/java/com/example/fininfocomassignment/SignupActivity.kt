package com.example.fininfocomassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fininfocomassignment.databinding.ActivitySignupBinding
import com.example.fininfocomassignment.model.ResponseData
import com.example.fininfocomassignment.viewmodel.SignUpViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel:SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        setUpRegisterListener()

        observeLiveUserData()
    }

    private fun observeLiveUserData() {

        viewModel.registerLiveData.observe(this){signupResponse ->

            when(signupResponse)
            {


                is ResponseData.SuccessMessage  ->
                {
                    showToast(signupResponse.successMessage.toString())

                    startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                    finish()
                }
                is ResponseData.ErrorMessage ->

                {
                    showToast(signupResponse.errorMessage)
                }



            }

                binding.btnSignup.visibility = View.VISIBLE
                binding.progressCircular.visibility = View.GONE

        }
    }

    private fun showToast(toastMessage: String) {

        Toast.makeText(this@SignupActivity, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRegisterListener() {
        binding.apply {
            btnSignup.setOnClickListener {
                val regUserName = editTextRegUsername.text.toString().trim()
                val regPassword = editTextRegPassword.text.toString().trim()
                val regConfirmPassword = editTextConfirmPassword.text.toString().trim()

                btnSignup.visibility = View.GONE
                progressCircular.visibility = View.VISIBLE

                viewModel.signup(regUserName,regPassword,regConfirmPassword)

            }
        }
    }
}