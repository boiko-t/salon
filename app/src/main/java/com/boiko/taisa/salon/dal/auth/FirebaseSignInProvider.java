package com.boiko.taisa.salon.dal.auth;

import android.app.Activity;
import android.content.Intent;

import com.boiko.taisa.salon.BuildConfig;
import com.boiko.taisa.salon.domain.entity.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class FirebaseSignInProvider implements SignInProvider {
    private Activity activity;

    public FirebaseSignInProvider(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void signIn(SignInMethod method) {
        switch (method) {
            case GOOGLE:
                signInGoogle();
        }
    }

    @Override
    public User getUser() {
        return null;
    }

    private void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_REQUEST_ID_TOKEN)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, SignInMethod.GOOGLE.getCode());
    }
}
