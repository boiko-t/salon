package com.boiko.taisa.salon.mvp.signin;

import com.boiko.taisa.salon.dal.auth.SignInMethod;
import com.boiko.taisa.salon.dal.auth.AuthProvider;
import com.boiko.taisa.salon.mvp.MVP;
import com.boiko.taisa.salon.mvp.signin.SignInModel;

public class SignInPresenter implements SignIn.Presenter {
    private SignIn.View view;
    private SignIn.Model model;
    private AuthProvider signInProvider;

    public SignInPresenter(AuthProvider signInProvider) {
        this.signInProvider = signInProvider;
        model = SignInModel.getInstance();
    }

    @Override
    public void onViewAttach(MVP.View view) {
        this.view = (SignIn.View) view;
    }

    @Override
    public void onViewDetach() {
        this.view = null;
    }

    @Override
    public void onSignInComplete(boolean success) {
        if (success)
            view.openHomeView();
    }

    @Override
    public void onSignInClick(SignInMethod method) {
        model.signIn(signInProvider, method);
    }

    @Override
    public void onSignUpViewClick() {
        view.openSignUpView();
    }
}
