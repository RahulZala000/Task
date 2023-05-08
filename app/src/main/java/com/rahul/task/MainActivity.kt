package com.rahul.task

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahul.task.databinding.ActivityMainBinding
import java.io.InputStream
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var requestcode=1

    lateinit var userData:List<Data>
    val DD_MM_YYYY = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    var activeuser:ArrayList<Data> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listType: Type = object : TypeToken<ArrayList<Data>>() {}.type
        userData = Gson().fromJson(getData(), listType)

        getFile()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.dpDate.setOnDateChangedListener(object : DatePicker.OnDateChangedListener {
                override fun onDateChanged(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    Toast.makeText(this@MainActivity,"$dayOfMonth/${monthOfYear+1}/$year",Toast.LENGTH_SHORT).show()

                    getActiveData(dayOfMonth,monthOfYear+1,year)
                }
            })
        }
    }

    private fun getActiveData(dayOfMonth: Int, monthOfYear: Int, year: Int) {

        var dd= if (dayOfMonth>9) "$dayOfMonth" else "0$dayOfMonth"
        var mm= if (monthOfYear>9) "$monthOfYear" else "0$monthOfYear"
        var date="$dd/$mm/$year"

        for (data in userData) {
            if (getDate(data.timestamp)== date)
            {
                activeuser.add(data)
            }
        }
        getActiveUser()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==requestcode && resultCode== Activity.RESULT_OK){
            if (data==null)
                return

            var uri=data.data

            val jsonSelectedFile =  contentResolver.openInputStream(uri!!);
            val inputAsString = jsonSelectedFile?.bufferedReader().use { it?.readText() }
            val listType: Type = object : TypeToken<ArrayList<Data>>() {}.type

            userData=Gson().fromJson(inputAsString,listType)
            Toast.makeText(this,userData.size.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFile(){
        var intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*")
        startActivityForResult(intent,requestcode)
    }

    private fun getActiveUser() {
        var hour:ArrayList<ArrayList<Int>> = ArrayList()
        for(i in 0..23) {

            var activeUser:ArrayList<Int> = ArrayList()
            for (data in activeuser) {

            getDate(data.timestamp)
                when (get24Hour(data.timestamp).toInt()) {
                    i -> {
                        activeUser.add(data.id)
                    }
                }
            }
            hour.add(activeUser)
        }

        binding.recyleview.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyleview.adapter=LogAdapter(hour)
    }


    private fun getData(): String? {
        var json:String?=null
        var input: InputStream?=null
        try{
            input=this.assets.open("usersobject.json")

            var size=input.available()

            var buffer=ByteArray(size)

            input.read(buffer)

            json=String(buffer)

            return json
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
        finally {
            input?.close()
        }
        return null
    }

    private fun get24Hour(time:String): String {

        val inputFormat = SimpleDateFormat(DD_MM_YYYY)
        val outputFormat = SimpleDateFormat("HH")
        var date: Date? = null
        try {
            date = inputFormat.parse(time)
            return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    return ""
    }

    fun getDate(d: String?): String? {
        val inputFormat = SimpleDateFormat(DD_MM_YYYY)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy")
        var date: Date? = null
        try {
            date = inputFormat.parse(d)
           return outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.toString()
    }
}