package com.example.hideandseek

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var verification = ""
    lateinit var mAuth: FirebaseAuth
    lateinit var callbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()


        recieve_otp.setOnClickListener {
            var phone_no:String = phone_no.text.toString()
            send_otp(phone_no)
        }

        submit_otp.setOnClickListener {
            auth()
        }
    }

    private fun verificationCallbacks(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                Toast.makeText(this@MainActivity,"Complete",Toast.LENGTH_SHORT).show()
                if (credential != null) {
                            signIn(credential)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                Toast.makeText(this@MainActivity,"Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(p0, p1)
                verification = p0.toString()
                otp.visibility = View.VISIBLE
                submit_otp.visibility = View.VISIBLE
            }

        }
    }



    private fun toast(data:String){
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show()
    }

    private fun send_otp(phone_no: String) {
        verificationCallbacks()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91"+phone_no,      // Phone number to verify
            60,               // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this,             // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks
    }

    private fun auth(){
        val auth = otp.text.toString()
        if (auth.equals(""))
        {
            Toast.makeText(this,"Please Enter OTP",Toast.LENGTH_SHORT).show()
        }else{
//            if(auth.equals(verification))
//            {
//                Toast.makeText(this,"Ok",Toast.LENGTH_SHORT).show()
//            }
        val credential:PhoneAuthCredential = PhoneAuthProvider.getCredential(verification,auth)
        signIn(credential)
        }
    }

    private fun signIn(credential: PhoneAuthCredential){
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                toast("Log In Successfully")
//                val i = Intent(this,User_reg::class.java)
//                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(i)
            }
        }
    }


}
