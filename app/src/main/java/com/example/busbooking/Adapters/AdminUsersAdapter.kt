package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.Models.UserModel
import com.example.busbooking.databinding.UserItemBinding

class AdminUsersAdapter(private val users:List<UserModel>):RecyclerView.Adapter<AdminUsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (users.isNullOrEmpty()){
            return 0
        }else{
            return users.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }


    inner class ViewHolder(private val binding:UserItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: UserModel) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email
            binding.userPhone.text = user.phone
        }

    }
}