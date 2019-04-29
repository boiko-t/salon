package com.boiko.taisa.salon.mvp.signup;

import com.boiko.taisa.salon.dal.auth.AuthProvider;
import com.boiko.taisa.salon.mvp.MVP;

import io.reactivex.Observable;

public interface SignUp {
    interface View extends MVP.View {
        void openHomeView();
        void showError();
    }

    interface Presenter extends MVP.Presenter {
        void onSignUpClick(String email, String password);
    }

    interface Model extends MVP.Model {
        void signUp(AuthProvider provider, String email, String password);
        Observable<State> getStateObservable();

        class State {
            public final boolean isSuccessful;

            State(boolean isSuccessful) {
                this.isSuccessful = isSuccessful;
            }
        }
    }
}
