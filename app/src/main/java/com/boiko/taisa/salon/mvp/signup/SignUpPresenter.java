package com.boiko.taisa.salon.mvp.signup;

import com.boiko.taisa.salon.dal.auth.AuthProvider;
import com.boiko.taisa.salon.mvp.MVP;
import com.boiko.taisa.salon.mvp.signin.SignIn;
import com.boiko.taisa.salon.mvp.signin.SignInModel;

public class SignUpPresenter implements SignUp.Presenter {
    private SignUp.View view;
    private SignUp.Model model;
    private AuthProvider signUpProvider;

    public SignUpPresenter(AuthProvider signUpProvider) {
        this.signUpProvider = signUpProvider;
        model = SignUpModel.getInstance();
    }

    @Override
    public void onViewAttach(MVP.View view) {
        this.view = (SignUp.View) view;
    }

    @Override
    public void onViewDetach() {
        this.view = null;
    }

    @Override
    public void onSignUpClick(String email, String password) {
        model.signUp(signUpProvider, email, password);
        model.getStateObservable()
                .subscribe(state -> {
                    if (!state.isSuccessful) view.showError();
                    else view.openHomeView();
                });
    }
}
