package com.rahul.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

          //  if (curr.size>0){
                card.visibility=View.VISIBLE
                tvHour.text="Time : $position to ${position+1} ${hour.size}"
                tvTotal.text="Total Active user : ${curr.size}"
                tvUser.text="Active user id : $curr"
            /*}
            else{
                card.visibility=View.GONE
            }*/
        }
    }

    override fun getItemCount(): Int {
        return hour.size
    }


}