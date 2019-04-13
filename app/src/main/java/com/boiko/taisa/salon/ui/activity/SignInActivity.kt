package com.boiko.taisa.salon.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.mvp.SignIn
import com.boiko.taisa.salon.mvp.SignInPresenter
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity(), SignIn.View {
    private lateinit var presenter: SignInPresenter
    private lateinit var btnLogIn: Button
    private lateinit var btnLogOut: Button
    private var RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        presenter = SignInPresenter()

        // Choose authentication providers
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build())

        btnLogIn = findViewById(R.id.btnLogIn)
        btnLogOut = findViewById(R.id.btnLogOut)

        btnLogOut.setOnClickListener { AuthUI.getInstance()
                .signOut(this) }

        btnLogIn.setOnClickListener { startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.logo)
                        .setTheme(R.style.AppTheme)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN) }


// Create and launch sign-in intent

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("AUTH", user.toString())
                Log.d("AUTH", user?.displayName)
                Log.d("AUTH", user?.email)
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
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
}
