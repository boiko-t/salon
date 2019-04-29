package com.boiko.taisa.salon.mvp.signin;

import com.boiko.taisa.salon.dal.auth.SignInMethod;
import com.boiko.taisa.salon.dal.auth.AuthProvider;
import com.boiko.taisa.salon.mvp.MVP;

public interface SignIn {
    interface View extends MVP.View {
        void openSignUpView();
        void openHomeView();
    }

    interface Presenter extends MVP.Presenter {
        void onSignInComplete(boolean success);
        void onSignInClick(SignInMethod method);
        void onSignUpViewClick();
    }

    interface Model extends MVP.Model {
        void signIn(AuthProvider provider, SignInMethod method);
    }
}
