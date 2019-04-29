package com.boiko.taisa.salon.mvp.signin;

import com.boiko.taisa.salon.dal.auth.AuthProvider;
import com.boiko.taisa.salon.dal.auth.SignInMethod;

public class SignInModel implements SignIn.Model {
    private static SignInModel INSTANCE;

    public static SignInModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SignInModel();
        }
        return INSTANCE;
    }

    @Override
    public void signIn(AuthProvider provider, SignInMethod method) {
        provider.signIn(method);
    }
}
