package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.mvp.SignIn
import com.boiko.taisa.salon.mvp.SignInPresenter
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent

class SignInActivity : AppCompatActivity(), SignIn.View {
    private lateinit var presenter: SignInPresenter
    private var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleApiClient: GoogleApiClient
    private var disposable = CompositeDisposable()

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
        setContentView(R.layout.activity_sign_in)
        presenter = SignInPresenter()
        findViews()
        initViewObservables()
        initSubscriptions()
    }

    override fun signInPassword() {
        presenter.signInPassword()
    }

    override fun signInFacebook() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signInGoogle() {
//        presenter.signInGoogle()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("244005455483-1pi7dnok5ch93nfvjtu7imfc90ofvgpr.apps.googleusercontent.com")
                .requestEmail()
                .build()

//        googleApiClient = GoogleApiClient.Builder(this)
//                .enableAutoManage(this@LoginActivity, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build()
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
//        presenter = null
        disposable.dispose()
        super.onDestroy()
    }

    private fun initViewObservables() {
        signInObservable = RxView.clicks(btnSignIn)
        signInGoogleObservable = RxView.clicks(btnSignInGoogle)
        signInFacebookObservable = RxView.clicks(btnSignInFacebook)
    }

    private fun initSubscriptions() {
        disposable.add(signInObservable.subscribe { signInPassword() })
        disposable.add(signInGoogleObservable.subscribe { signInGoogle() })
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
