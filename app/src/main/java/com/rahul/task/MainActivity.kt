package com.rahul.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.rangeTo
import com.google.gson.Gson
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    lateinit var userData:LogResponseModel
    val DD_MM_YYYY = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    var loginData:ArrayList<Data> = ArrayList()
    var logoutData:ArrayList<Data> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userData= Gson().fromJson(getData(),LogResponseModel::class.java)

       // get24Hour(userData.data[0].timestamp)


        getActiveUser(23)

    }

    private fun getActiveUser(no:Int) {
        var total=0
        for (data in userData.data){

            when(get24Hour(data.timestamp).toInt()){
                no-> {
                    ++total
                    Log.d("@total sub",data.id.toString())
                }
            }
        }
        Log.d("@total",total.toString())

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
           // Log.d("@data",json)
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


        var date: Date? = null

        val dateFormat = SimpleDateFormat(DD_MM_YYYY)
        date = dateFormat.parse(time)
        val dateString = date.toString()
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
        val outputFormat = SimpleDateFormat("HH")
        try {
         date = inputFormat.parse(dateString)
        var timeData = outputFormat.format(date)
            Log.d("@date",timeData)
            return timeData
        }catch (e: ParseException) {
                e.printStackTrace()
        }

    return " Empty"
    }
}