package com.boiko.taisa.salon.dal.auth;

import com.boiko.taisa.salon.domain.entity.User;

public interface SignInProvider {
    void signIn(SignInMethod method);
    User getUser();
}
