package com.rahul.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahul.task.databinding.LayoutCardBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LogAdapter(var hour: ArrayList<Data>):RecyclerView.Adapter<LogAdapter.MyViewHolder>() {

    val DD_MM_YYYY = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    class MyViewHolder(var binding:LayoutCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var curr=hour[position]

        holder.binding.apply {

          //  if (curr.size>0){
               // card.visibility=View.VISIBLE
                tvHour.text="Time : ${get24Hour(curr.timestamp)} to ${get24Hour(curr.timestamp)+1}"
               // tvTotal.text="Total Active user : ${curr}"
                tvUser.text="Active user id : ${curr.id}"
            /*}
            else{
                card.visibility=View.GONE
            }*/
        }
    }

    private fun get24Hour(time:String): Int {

        val inputFormat = SimpleDateFormat(DD_MM_YYYY)
        val outputFormat = SimpleDateFormat("HH")
        var date: Date? = null
        try {
            date = inputFormat.parse(time)
            return outputFormat.format(date).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 1
    }

    override fun getItemCount(): Int {
        return hour.size
    }


}