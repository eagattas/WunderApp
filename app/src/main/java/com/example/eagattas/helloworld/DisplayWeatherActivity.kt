package com.example.eagattas.helloworld

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View
import android.widget.TextView;
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import android.os.StrictMode
import org.json.JSONObject


class DisplayWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_weather)

        // Get the Intent that started this activity and extract the string
        val intent = intent
        val type = intent.getStringExtra(MainActivity().EXTRA_TYPE)
        val location = intent.getStringExtra(MainActivity().EXTRA_LOCATION)
        val query = intent.getStringExtra(MainActivity().EXTRA_QUERY)

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById(R.id.textView) as TextView

        //do the weather request
        Fuel.get("http://api.wunderground.com/api/bb06c2cc46b81945/almanac/forecast/astronomy/q/$query.json").responseJson { _, _, result ->
            val (json, _) = result
            if (json != null) {
                val jsonObject = json.obj()
                if (!json.obj().getJSONObject("response").has("error")) {
                    if(type == "Today") {//today's forecast
                        val icon_url = jsonObject.getJSONObject("forecast").getJSONObject("txt_forecast")
                                .getJSONArray("forecastday").getJSONObject(0).getString("icon_url")

                        textView.text = getForecastString(jsonObject, location)

                        loadImage(icon_url, findViewById(R.id.weatherIcon) as ImageView)
                    }

                    else{//historic weather info

                        textView.text = getHistoricString(jsonObject, location)
                    }
                }
                else {
                    textView.text = "Please enter a valid zip code."
                }
            }
            else{
                textView.text = "Please try another request."
            }
        }

    }

    fun newRequest(view: View) {
        finish()
    }

    fun getForecastString(jsonObject: JSONObject, location: String) :String{
        val forecastText = jsonObject.getJSONObject("forecast").getJSONObject("txt_forecast")
                .getJSONArray("forecastday").getJSONObject(0).getString("fcttext")
        val currentTime = jsonObject.getJSONObject("moon_phase").getJSONObject("current_time")
        val timeString = currentTime.getString("hour") + ":" + currentTime.getString("minute")
        return "Today's Forecast for $location\n\n$forecastText\n\nCurrent Time is $timeString"
    }

    fun getHistoricString(jsonObject: JSONObject, location: String) :String{
        val highs = jsonObject.getJSONObject("almanac").getJSONObject("temp_high")
        val recordHigh = highs.getJSONObject("record").getString("F")
        val averageHigh = highs.getJSONObject("normal").getString("F")
        val lows = jsonObject.getJSONObject("almanac").getJSONObject("temp_low")
        val recordLow = lows.getJSONObject("record").getString("F")
        val averageLow = lows.getJSONObject("normal").getString("F")
        val currentTime = jsonObject.getJSONObject("moon_phase").getJSONObject("current_time")
        val timeString = currentTime.getString("hour") + ":" + currentTime.getString("minute")
        return "Today's Historic Temperatures for $location\n\nAverage High: $averageHigh F\n" +
                "Average Low: $averageLow F\n\nRecord High: $recordHigh F\nRecord Low: $recordLow F\n\nCurrent Time is $timeString"
    }

    fun loadImage(icon_url: String, i: ImageView){
        try {
            val url = URL(icon_url)
            val inputstream = url.openConnection().getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputstream)
            i.setImageBitmap(bitmap)
            i.visibility = View.VISIBLE
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
