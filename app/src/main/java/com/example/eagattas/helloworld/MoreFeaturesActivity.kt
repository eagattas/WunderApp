package com.example.eagattas.helloworld

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MoreFeaturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_features)

        MainActivity().underlineText(findViewById(R.id.moreFeaturesText) as TextView)
    }

    fun startCompareCities(view: View){
        val intent = Intent(this, CompareCitiesActivity::class.java)
        startActivity(intent)
    }
}
