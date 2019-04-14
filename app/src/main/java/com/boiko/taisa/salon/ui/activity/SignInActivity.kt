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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.text.method.LinkMovementMethod
import android.util.Log
import com.boiko.taisa.salon.BuildConfig
import com.boiko.taisa.salon.mvp.SignIn.View.RC_GOOGLE_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


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
    private lateinit var btnSignInFacebook: Button
    private lateinit var signInObservable: Observable<Any>
    private lateinit var signInGoogleObservable: Observable<Any>
    private lateinit var signInFacebookObservable: Observable<Any>
    private lateinit var signUpLinkObservable: Observable<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        presenter = SignInPresenter()
        disposable = CompositeDisposable()
        findViews()
        configureViews()
        initViewObservables()
        initSubscriptions()
    }

    override fun openSignUpView() {
        Log.d(TAG, "cliiiiick")
    }

    override fun signInPassword() {
        presenter.signInPassword()
    }

    override fun signInFacebook() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_REQUEST_ID_TOKEN)
                .requestEmail()
                .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
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
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                    } else {
                    }
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

    private fun initViewObservables() {
        signInObservable = RxView.clicks(btnSignIn)
        signInGoogleObservable = RxView.clicks(btnSignInGoogle)
        signInFacebookObservable = RxView.clicks(btnSignInFacebook)
        signUpLinkObservable = RxView.clicks(tvSignUpLink)
    }

    private fun initSubscriptions() {
        disposable.add(signInObservable.subscribe { signInPassword() })
        disposable.add(signInGoogleObservable.subscribe { signInGoogle() })
        disposable.add(signUpLinkObservable.subscribe { openSignUpView() })
    }

    private fun findViews() {
        etEmail = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        tvSignUpLink = findViewById(R.id.tvSignUpLink)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        btnSignInFacebook = findViewById(R.id.btnSignInFacebook)
    }

    private fun configureViews() {
        tvSignUpLink.movementMethod = LinkMovementMethod.getInstance()
    }
}
