package com.example.fininfocomassignment.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fininfocomassignment.databinding.ActivityMainBinding
import com.example.fininfocomassignment.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.fetchUser("name")
        userAdapter = UserAdapter()


        prepareUserData()
        observeUserData()

        binding.imgSortBy.setOnClickListener {
            showSortingOptions()
        }


    }

    private fun observeUserData() {
        viewModel.userLiveData.observe(this){userLiveData ->
            userAdapter.setUser(userLiveData)

        }
    }

    private fun prepareUserData() {
        binding.rcvUserDetails.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = userAdapter

        }
    }


    private fun showSortingOptions() {
        val options = arrayOf("Sort by Name", "Sort by Age", "Sort by City")

        AlertDialog.Builder(this)
            .setTitle("Sort Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> viewModel.fetchUser("name")
                    1 -> viewModel.fetchUser("age")
                    2 -> viewModel.fetchUser("city")
                }
            }
            .show()
    }
}