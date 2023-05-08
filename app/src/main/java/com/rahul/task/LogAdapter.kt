package com.rahul.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahul.task.databinding.LayoutCardBinding

class LogAdapter(var hour: ArrayList<ArrayList<Int>>):RecyclerView.Adapter<LogAdapter.MyViewHolder>() {

    class MyViewHolder(var binding:LayoutCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var curr=hour[position]

        holder.binding.apply {
            tvHour.text="Time : $position to ${position+1}"
            tvTotal.text="Total Active user : ${curr.size}"
            tvUser.text="Active user id : $curr"
        }
    }

    override fun getItemCount(): Int {
        return hour.size
    }


}