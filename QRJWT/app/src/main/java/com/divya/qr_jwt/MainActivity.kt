package com.divya.qr_jwt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.ImageButton
import android.widget.TextView
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val qrButton: ImageButton = findViewById(R.id.qr_button)
        qrButton.setOnClickListener{
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        val Data = result.contents
        val decodedJWT:DecodedJWT
        Log.i("hello", "onActivityResult: ${Data} ")
        val algorithm:Algorithm = Algorithm.HMAC256("neofolks2023")
        Log.i("hello", "onActivityResult: ${algorithm} ")
        val verifier = JWT.require(algorithm).build()
        Log.i("hello", "onActivityResult: ${verifier} ")
        decodedJWT = verifier.verify(Data)
        val name = decodedJWT.getClaim("name").asString()
        val email = decodedJWT.getClaim("email").asString()
        val phnum = decodedJWT.getClaim("phone").asString()
        val teamname = decodedJWT.getClaim("teamName").asString()
        Log.i("hello", "onActivityResult: ${name} ")
        val textView2: TextView = findViewById(R.id.result1)
        textView2.text="${name}  ${email}  ${phnum}  ${teamname}"

    }

}