package com.example.eagattas.helloworld

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.graphics.Paint
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    val EXTRA_TYPE = "com.example.eagattas.helloworld.TYPE"
    val EXTRA_LOCATION = "com.example.eagattas.helloworld.LOCATION"
    val EXTRA_QUERY = "com.example.eagattas.helloworld.QUERY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //underline headings
        underlineText(findViewById(R.id.chooseLocationView) as TextView)
        underlineText(findViewById(R.id.chooseWeatherType) as TextView)

        //create listener for Other button
        createOnCheckedListener(findViewById(R.id.locationGroup) as RadioGroup, findViewById(R.id.editZipCode))

    }

    fun createOnCheckedListener(radioGroup: RadioGroup, view: View){
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                val selectedLocation = findViewById(radioGroup.checkedRadioButtonId) as RadioButton
                if (selectedLocation.text == "Other"){
                    view.visibility = View.VISIBLE
                }
                else{
                    view.visibility = View.INVISIBLE
                }
            }
        })
    }

    fun underlineText(view: TextView){
        view.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    fun sendMessage(view: View) {
        val intent = Intent(this, DisplayWeatherActivity::class.java)

        //figure out what's selected
        val typeGroup = findViewById(R.id.typeGroup) as RadioGroup
        val selectedType = findViewById(typeGroup.checkedRadioButtonId) as RadioButton
        val locationGroup = findViewById(R.id.locationGroup) as RadioGroup
        val selectedLocation = findViewById(locationGroup.checkedRadioButtonId) as RadioButton

        //set intent extras
        intent.putExtra(EXTRA_TYPE, selectedType.text)
        if(selectedLocation.text=="Other"){
            val zip = findViewById(R.id.editZipCode) as EditText
            intent.putExtra(EXTRA_LOCATION, "Zip Code " + zip.text.toString())
            intent.putExtra(EXTRA_QUERY, zip.text.toString())
        }
        else{
            intent.putExtra(EXTRA_LOCATION, selectedLocation.text)
            intent.putExtra(EXTRA_QUERY, selectedLocation.contentDescription)
        }
        startActivity(intent)
    }

    fun moreFeatures(view: View){
        val intent = Intent(this, MoreFeaturesActivity::class.java)
        startActivity(intent)
    }


}
