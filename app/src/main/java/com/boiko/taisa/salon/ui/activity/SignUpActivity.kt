package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.boiko.taisa.salon.R
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import android.content.Intent
import android.support.v7.app.ActionBar
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    companion object {
        const val TAG = "SignUpActivity"
    }
    private lateinit var disposable: CompositeDisposable
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        disposable = CompositeDisposable()
        findViews()
        initSubscriptions()
        configureViews()
    }

    override fun onStart() {
        super.onStart()
//        presenter.onViewAttach(this)
    }

    override fun onStop() {
//        presenter.onViewDetach()
        super.onStop()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun initSubscriptions() {
        val signUpObservable = RxView.clicks(btnSignUp)
        val emailInputObservable = RxTextView.textChanges(etEmailAddress)

        disposable.add(emailInputObservable.subscribe { v -> Log.d(TAG, v.toString()) })
    }

    private fun findViews() {
        etEmail = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
    }

    private fun configureViews() {
        val toolbar = supportActionBar
        toolbar?.title = getString(R.string.sign_up_name)
        toolbar?.setDisplayHomeAsUpEnabled(true)
    }
}
