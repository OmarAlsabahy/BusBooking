package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.R
import com.example.busbooking.databinding.TableItemBinding

class TypesAdapter(private val types:List<String>):RecyclerView.Adapter<TypesAdapter.ViewHolder>() {
    var clickedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TableItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(types.isNullOrEmpty()){
            return 0
        }else{
            return types.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(types[position],position)
        holder.binding.btnType.setOnClickListener {
            if (clickedPosition!=position){
                clickedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(val binding:TableItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(type: String,position: Int) {
            if (clickedPosition==position)
            {
                binding.btnType.setBackgroundColor(binding.btnType.context.getColor(R.color.mainColor))
                binding.btnType.setTextColor(binding.btnType.context.getColor(R.color.white))
            }else{
                binding.btnType.setBackgroundColor(binding.btnType.context.getColor(android.R.color.transparent))
                binding.btnType.setTextColor(binding.btnType.context.getColor(R.color.mainColor))
            }
            binding.btnType.text = type
        }

    }
}