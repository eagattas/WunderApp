package com.example.eagattas.helloworld

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class CompareCitiesActivity : AppCompatActivity() {

    val EXTRA_LOCATION1 = "com.example.eagattas.helloworld.LOCATION1"
    val EXTRA_QUERY1 = "com.example.eagattas.helloworld.QUERY1"
    val EXTRA_LOCATION2 = "com.example.eagattas.helloworld.LOCATION2"
    val EXTRA_QUERY2 = "com.example.eagattas.helloworld.QUERY2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_cities)

        //underline headings
        MainActivity().underlineText(findViewById(R.id.location1Text) as TextView)
        MainActivity().underlineText(findViewById(R.id.location2Text) as TextView)

        //create listeners for Other checked

        val radioGroup1 = findViewById(R.id.locationGroup1) as RadioGroup
        radioGroup1.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                val selectedLocation = findViewById(radioGroup1.checkedRadioButtonId) as RadioButton
                if (selectedLocation.text == "Other"){
                    findViewById(R.id.editZipCode1).visibility = View.VISIBLE
                }
                else{
                    findViewById(R.id.editZipCode1).visibility = View.INVISIBLE
                }
            }
        })

        val radioGroup2 = findViewById(R.id.locationGroup2) as RadioGroup
        radioGroup2.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                val selectedLocation = findViewById(radioGroup2.checkedRadioButtonId) as RadioButton
                if (selectedLocation.text == "Other"){
                    findViewById(R.id.editZipCode2).visibility = View.VISIBLE
                }
                else{
                    findViewById(R.id.editZipCode2).visibility = View.INVISIBLE
                }
            }
        })

    }

    fun compareConfirm(view: View){
        val intent = Intent(this, DisplayCompareActivity::class.java)

        //the rest is setting the extras to the selected location values

        val locationGroup1 = findViewById(R.id.locationGroup1) as RadioGroup
        val selectedLocation1 = findViewById(locationGroup1.checkedRadioButtonId) as RadioButton

        if(selectedLocation1.text=="Other"){
            val zip = findViewById(R.id.editZipCode1) as EditText
            intent.putExtra(EXTRA_LOCATION1, "Zip Code " + zip.text.toString())
            intent.putExtra(EXTRA_QUERY1, zip.text.toString())
        }
        else{
            intent.putExtra(EXTRA_LOCATION1, selectedLocation1.text)
            intent.putExtra(EXTRA_QUERY1, selectedLocation1.contentDescription)
        }

        val locationGroup2 = findViewById(R.id.locationGroup2) as RadioGroup
        val selectedLocation2 = findViewById(locationGroup2.checkedRadioButtonId) as RadioButton

        if(selectedLocation2.text=="Other"){
            val zip = findViewById(R.id.editZipCode2) as EditText
            intent.putExtra(EXTRA_LOCATION2, "Zip Code " + zip.text.toString())
            intent.putExtra(EXTRA_QUERY2, zip.text.toString())
        }
        else{
            intent.putExtra(EXTRA_LOCATION2, selectedLocation2.text)
            intent.putExtra(EXTRA_QUERY2, selectedLocation2.contentDescription)
        }

        startActivity(intent)
    }



}
