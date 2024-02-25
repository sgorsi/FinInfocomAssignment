package com.example.fininfocomassignment.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fininfocomassignment.databinding.ItemUserBinding
import com.example.fininfocomassignment.model.UserData

class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private  val userList =  mutableListOf<UserData>()

    fun setUser(newUser : List<UserData>)
    {
        userList.clear()
        userList.addAll(newUser)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bind(userList[position])
    }

    class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) {
            binding.apply {
                tvUserName.text = userData.name
                tvUserAge.text = userData.age.toString()
                tvUserCity.text = userData.city
                tvUserZipCode.text = userData.zip.toString()
            }

        }

    }
}