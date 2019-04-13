package com.boiko.taisa.salon.mvp;

import android.util.Log;

public class SignInPresenter implements SignIn.Presenter {
    private SignIn.View view;

    @Override
    public void onViewAttach(MVP.View view) {
        this.view = (SignIn.View)view;
    }

    @Override
    public void onViewDetach() {
        this.view = null;
    }

    @Override
    public void signInPassword() {

    }

    @Override
    public void signInFacebook() {

    }

    @Override
    public void signInGoogle() {
        Log.d("taisa", "signInGoogle: goooogle");
    }
}
