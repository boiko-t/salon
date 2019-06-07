package com.boiko.taisa.salon.dal.auth;

public enum SignInMethod  {
    EMAIL(100), GOOGLE(300);

    private int code;

    SignInMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
