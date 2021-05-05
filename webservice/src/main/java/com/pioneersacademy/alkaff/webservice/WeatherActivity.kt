package com.pioneersacademy.alkaff.webservice

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.pioneersacademy.alkaff.webservice.databinding.ActivityWeatherBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WeatherActivity : AppCompatActivity() {

    companion object {
        val TAG:String = "WeatherActivity"
    }
    private lateinit var binding:ActivityWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonLoad.setOnClickListener { loadWeather(editTextTextCity.text.toString()) }
        }

    }

    private fun loadWeather(city: String?) {

        Log.d(TAG,"Thread name:${Thread.currentThread().name}")
        // this will sleep the main UI thread for 20000 ms
        //Thread.sleep(20000)

        val appKey = "c3cc7c90499af31599728a6284ff37f1"
        //api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
        val urlString = "https://api.openweathermap.org/data/2.5/weather?q=${city?.trim()}&units=metric&mode=json&appid=${appKey}&lang=ar"

        // To perform the connection in the main UI thread (Wrong to be done in the main UI thread )
//        val url = URL(urlString)
//        val urlConnection = url.openConnection() as HttpURLConnection
//        urlConnection.connectTimeout = 7000
//        var inString= ConvertStreamToString(urlConnection.inputStream)
//        binding.textViewResult.setText(formatJSONString(inString))
        //LoadWeatherData().execute(url)

        // this to call the Async task need to be executed from the main UI thread
        MyAsync().execute(urlString)
    }

    inner class MyAsync: AsyncTask<String, Double, String>() {
        // Executed in the main UI before executing doInBackground
        override fun onPreExecute() {
            Log.d(TAG,"onPreExecute -> Thread name:${Thread.currentThread().name}")
            binding.textViewResult.setText("")
        }


        // This method will be executed in a worker thread
        override fun doInBackground(vararg params: String?): String {

            Log.d(TAG,"doInBackground -> Thread name:${Thread.currentThread().name}")
//            Log.d(TAG,params[0].toString())
//            Log.d(TAG,"Thread name:${Thread.currentThread().name} will start sleeping..")
//            Thread.sleep(20000)
//            Log.d(TAG,"Thread name:${Thread.currentThread().name} is running again")

            try {

                val url = URL(params[0])
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 7000
                var inString= ConvertStreamToString(urlConnection.inputStream)
                return  inString

            }catch (e:Exception){
                return  e.stackTraceToString()
            }

        }

        // This method will be executed in the main UI after executing doInBackground
        override fun onPostExecute(result: String?) {
            Log.d(TAG,"onPostExecute -> Thread name:${Thread.currentThread().name}")
            binding.textViewResult.setText(result)
            Toast.makeText(this@WeatherActivity,getMainweather(result),Toast.LENGTH_LONG).show()
        }



    }

    inner class  LoadWeatherData: AsyncTask<String, Double, String>() {
        override fun doInBackground(vararg params: String?): String {
            try {
                val url= URL(params[0])

                val urlConnect=url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout=7000

                var inString= ConvertStreamToString(urlConnect.inputStream)


                return inString

            }catch (e: Exception){
               throw  e
            }
            return ""

        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            binding.apply {
                textViewResult.setText(result)
                editTextTextCity.setText("")
            }
           //Toast.makeText(this@WeatherActivity, result?.let { getTemp(it).toString() },Toast.LENGTH_LONG).show()

        }

        override fun onProgressUpdate(vararg values: Double?) {
            super.onProgressUpdate(*values)
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
        }

        override fun onCancelled() {
            super.onCancelled()
        }
    }

    private fun ConvertStreamToString(inputStream: InputStream):String{

        val bufferReader= BufferedReader(InputStreamReader(inputStream))
        var line:String?
        var AllString=""

        try {
            do{
                line=bufferReader.readLine()
                if(line!=null){
                    AllString+=line
                }
            }while (line!=null)
            inputStream.close()
        }catch (ex: Exception){}

        Log.d(TAG,AllString)
        return formatJSONString(AllString)
    }

    private fun formatJSONString(json: String?):String{

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jp = JsonParser()
        return gson.toJson(jp.parse(json))
    }

    private fun getTemp(jsonString:String):Double {
        try {
            val jsonObject = JSONObject(jsonString)
            val main = jsonObject.getJSONObject("main")
            val temp = main.getDouble("temp")
            return  temp
        }catch (e:Exception){
            return  0.0
        }


    }

    private fun getMainweather(jsonString:String?):String {
        try {
            val jsonObject = JSONObject(jsonString)
            val weatherArray = jsonObject.getJSONArray("weather")
            val weatherObject = weatherArray.getJSONObject(0)
            val main = weatherObject.getString("main")

            return  main
        }catch (e:Exception)
        {
            return  ""
        }


    }
}