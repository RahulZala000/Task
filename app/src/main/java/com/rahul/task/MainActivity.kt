package com.rahul.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rahul.task.databinding.ActivityMainBinding
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    lateinit var userData:LogResponseModel
    val DD_MM_YYYY = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    var hour:ArrayList<ArrayList<Int>> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData= Gson().fromJson(getData(),LogResponseModel::class.java)

        getActiveUser()

    }

    private fun getActiveUser() {

        for(i in 0..23) {
//            var total = 0
            var activeUser:ArrayList<Int> = ArrayList()
            for (data in userData.data) {

                when (get24Hour(data.timestamp).toInt()) {
                    i -> {
//                        ++total
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
}