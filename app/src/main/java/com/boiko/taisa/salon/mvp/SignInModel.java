package com.boiko.taisa.salon.mvp;

import com.boiko.taisa.salon.dal.auth.SignInMethod;
import com.boiko.taisa.salon.dal.auth.SignInProvider;

public class SignInModel implements SignIn.Model {
    private static SignInModel INSTANCE;

    public static SignInModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SignInModel();
        }
        return INSTANCE;
    }

    @Override
    public void signIn(SignInProvider provider, SignInMethod method) {
        provider.signIn(method);
    }
}
