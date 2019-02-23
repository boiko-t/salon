package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.mvp.Salon

class HomeActivity : AppCompatActivity(), Salon.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
