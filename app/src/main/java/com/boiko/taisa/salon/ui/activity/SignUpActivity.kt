package com.boiko.taisa.salon.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.dal.auth.CustomFirebaseAuthProvider
import com.boiko.taisa.salon.mvp.signup.SignUp
import com.boiko.taisa.salon.mvp.signup.SignUpPresenter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity(), SignUp.View {
    companion object {
        const val TAG = "SignUpActivity"
        const val MIN_PASSWORD_LENGTH = 6
        const val EMAIL_PATTERN = ""
    }

    private lateinit var presenter: SignUp.Presenter
    private lateinit var disposable: CompositeDisposable
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        presenter = SignUpPresenter(CustomFirebaseAuthProvider(this))
        disposable = CompositeDisposable()
        findViews()
        initSubscriptions()
        configureViews()
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttach(this)
    }

    override fun onStop() {
        presenter.onViewDetach()
        super.onStop()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun showError() {
        Toast.makeText(applicationContext, getString(R.string.sign_up_error), Toast.LENGTH_SHORT)
                .show()
    }

    override fun openHomeView() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun initSubscriptions() {
        val signUpObservable = RxView.clicks(btnSignUp).debounce(300, TimeUnit.MILLISECONDS)

        disposable.add(signUpObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val isEmailValid = checkEmail(true)
                    val isPasswordValid = checkPassword(true)
                    if(isEmailValid && isPasswordValid)
                        presenter.onSignUpClick(etEmail.text.toString(), etPassword.text.toString())
                }
        )
    }

    private fun checkPassword(showError: Boolean = false): Boolean {
        val isValid = etPassword.text.length >= MIN_PASSWORD_LENGTH
        if (!isValid && showError) {
            etPassword.error = getString(R.string.password_length_error)
        }
        return isValid
    }

    private fun checkEmail(showError: Boolean = false): Boolean {
        val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}\$")
        val isValid = regex.containsMatchIn(etEmail.text)
        if (!isValid && showError) {
            etEmail.error = getString(R.string.email_error)
        }
        return isValid
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
