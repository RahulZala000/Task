package com.rahul.task

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rahul.task.databinding.ActivityMainBinding
import java.io.InputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    lateinit var userData:LogResponseModel
    val DD_MM_YYYY = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    Log.d("@id user",data.id.toString())
                }
            }
        }
        binding.tvActiveUser.text="$no to ${no+1} hour in active user : $total"
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