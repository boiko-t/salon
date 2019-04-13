package com.boiko.taisa.salon.mvp;

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
}
