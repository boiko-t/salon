package com.boiko.taisa.salon.dal.auth;

import com.boiko.taisa.salon.domain.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;

public interface AuthProvider {
    void signIn(SignInMethod method);
    boolean signUp(String email, String password);
    User getUser();
}
