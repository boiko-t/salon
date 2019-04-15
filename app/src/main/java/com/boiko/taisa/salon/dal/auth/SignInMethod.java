package com.boiko.taisa.salon.dal.auth;

public enum SignInMethod  {
    EMAIL(10), FACEBOOK(200), GOOGLE(100);

    private int code;

    SignInMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
