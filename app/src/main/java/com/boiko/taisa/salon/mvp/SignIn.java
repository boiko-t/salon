package com.boiko.taisa.salon.mvp;

import com.boiko.taisa.salon.dal.auth.SignInMethod;
import com.boiko.taisa.salon.dal.auth.SignInProvider;

public interface SignIn {
    interface View extends MVP.View {
        void openSignUpView();
    }

    interface Presenter extends MVP.Presenter {
        void onSignInComplete(boolean success);
        void onSignInClick(SignInMethod method);
        void onSignUpViewClick();
    }

    interface Model extends MVP.Model {
        void signIn(SignInProvider provider, SignInMethod method);
    }
}
