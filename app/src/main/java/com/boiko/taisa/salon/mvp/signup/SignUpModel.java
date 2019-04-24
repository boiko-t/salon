package com.boiko.taisa.salon.mvp.signup;

import com.boiko.taisa.salon.dal.auth.AuthProvider;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SignUpModel implements SignUp.Model {
    private static SignUpModel INSTANCE;
    private BehaviorSubject<State> state = BehaviorSubject.create();

    public static SignUpModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SignUpModel();
        }
        return INSTANCE;
    }

    @Override
    public void signUp(AuthProvider provider, String email, String password) {
        boolean result = provider.signUp(email, password);
        state.onNext(new State(result));
    }

    @Override
    public Observable<State> getStateObservable() {
        return state;
    }
}
