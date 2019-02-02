package com.example.hideandseek

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit_otp.setOnClickListener {
            var phone_no:String = phone_no.text.toString()
            var otp:String = otp.text.toString()
            Toast.makeText(this,phone_no+" OTP:"+otp,Toast.LENGTH_SHORT).show()
        }
    }
}
