package com.boiko.taisa.salon.dal.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.boiko.taisa.salon.BuildConfig;
import com.boiko.taisa.salon.domain.entity.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomFirebaseAuthProvider implements AuthProvider {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private Activity activity;

    public CustomFirebaseAuthProvider(Activity activity) {
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
    public boolean signUp(String email, String password) {
        final boolean[] result = new boolean[1];
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   result[0] = task.isSuccessful();
                }
            });
        return result[0];
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
