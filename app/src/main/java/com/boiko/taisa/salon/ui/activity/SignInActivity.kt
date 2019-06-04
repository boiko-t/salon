package com.boiko.taisa.salon.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.dal.auth.CustomFirebaseAuthProvider
import com.boiko.taisa.salon.dal.auth.SignInMethod
import com.boiko.taisa.salon.mvp.signin.SignIn
import com.boiko.taisa.salon.mvp.signin.SignInPresenter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable


class SignInActivity : AppCompatActivity(), SignIn.View {
    companion object {
        const val TAG = "SignInActivity"
    }

    private lateinit var presenter: SignInPresenter
    private lateinit var disposable: CompositeDisposable
    private var firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var tvSignUpLink: TextView
    private lateinit var btnSignInGoogle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        presenter = SignInPresenter(CustomFirebaseAuthProvider(this))
        disposable = CompositeDisposable()
        findViews()
        configureViews()
        initSubscriptions()
    }

    override fun openSignUpView() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    override fun openHomeView() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SignInMethod.GOOGLE.code) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed " + e.statusCode)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    presenter.onSignInComplete(task.isSuccessful)
                }
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

    private fun initSubscriptions() {
        val signInObservable = RxView.clicks(btnSignIn)
        val signInGoogleObservable = RxView.clicks(btnSignInGoogle)
        val signUpLinkObservable = RxView.clicks(tvSignUpLink)

        disposable.add(signInObservable.subscribe { presenter.onSignInClick(SignInMethod.EMAIL) })
        disposable.add(signInGoogleObservable.subscribe { presenter.onSignInClick(SignInMethod.GOOGLE) })
        disposable.add(signUpLinkObservable.subscribe { presenter.onSignUpViewClick() })
    }

    private fun findViews() {
        etEmail = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        tvSignUpLink = findViewById(R.id.tvSignUpLink)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
    }

    private fun configureViews() {
        initSubscriptions()
        supportActionBar?.title = getString(R.string.sign_in_name)
        tvSignUpLink.movementMethod = LinkMovementMethod.getInstance()
    }
}
