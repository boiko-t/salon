package com.boiko.taisa.salon.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.boiko.taisa.salon.R
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var tvSignUpLink: TextView
    private lateinit var btnSignInGoogle: Button
    private lateinit var btnSignInFacebook: Button
    private lateinit var signInObservable: Observable<Any>
    private lateinit var signInGoogleObservable: Observable<Any>
    private lateinit var signInFacebookObservable: Observable<Any>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViews()
        initViewObservables()
        initSubscriptions()
    }

    private fun initViewObservables() {
        signInObservable = RxView.clicks(btnSignIn)
        signInGoogleObservable = RxView.clicks(btnSignIn)
        signInFacebookObservable = RxView.clicks(btnSignIn)
    }

    private fun initSubscriptions() {
        signInObservable.subscribe({args -> Log.d("My tag", args.toString())})
    }

    private fun findViews() {
        etEmail = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        tvSignUpLink = findViewById(R.id.tvSignUpLink)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        btnSignInFacebook = findViewById(R.id.btnSignInFacebook)
    }
}
