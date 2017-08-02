package com.example.eagattas.helloworld

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson

class DisplayCompareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_compare)

        //get the extra values
        val intent = intent
        val location1 = intent.getStringExtra(CompareCitiesActivity().EXTRA_LOCATION1)
        val location2 = intent.getStringExtra(CompareCitiesActivity().EXTRA_LOCATION2)
        val query1 = intent.getStringExtra(CompareCitiesActivity().EXTRA_QUERY1)
        val query2 = intent.getStringExtra(CompareCitiesActivity().EXTRA_QUERY2)

        // Capture the layout's TextView and set the string as its text
        val textView1 = findViewById(R.id.forecast1) as TextView
        val textView2 = findViewById(R.id.forecast2) as TextView

        Fuel.get("http://api.wunderground.com/api/bb06c2cc46b81945/forecast/astronomy/q/$query1.json").responseJson { _, _, result ->
            val (json, _) = result
            if (json != null) {
                val jsonObject = json.obj()
                if(!json.obj().getJSONObject("response").has("error")) {

                    val icon_url = jsonObject.getJSONObject("forecast").getJSONObject("txt_forecast")
                            .getJSONArray("forecastday").getJSONObject(0).getString("icon_url")

                    textView1.text = DisplayWeatherActivity().getForecastString(jsonObject, location1)

                    DisplayWeatherActivity().loadImage(icon_url, findViewById(R.id.weatherIcon1) as ImageView)
                }
                else{
                    textView1.text = "Please enter a valid zip code."
                }
            }
            else{
                textView1.text = "Please try another request."
            }
        }

        Fuel.get("http://api.wunderground.com/api/bb06c2cc46b81945/forecast/astronomy/q/$query2.json").responseJson { _, _, result ->
            val (json, _) = result
            if (json != null) {
                val jsonObject = json.obj()
                if(!json.obj().getJSONObject("response").has("error")) {

                    val icon_url = jsonObject.getJSONObject("forecast").getJSONObject("txt_forecast")
                            .getJSONArray("forecastday").getJSONObject(0).getString("icon_url")

                    textView2.text = DisplayWeatherActivity().getForecastString(jsonObject, location2)

                    DisplayWeatherActivity().loadImage(icon_url, findViewById(R.id.weatherIcon2) as ImageView)

                }
                else{
                    textView2.text = "Please enter a valid zip code."
                }
            }
            else{
                textView2.text = "Please try another request."
            }
        }
    }
}
