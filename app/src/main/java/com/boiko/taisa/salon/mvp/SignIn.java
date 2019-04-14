package com.boiko.taisa.salon.mvp;

public interface SignIn {
    enum SignInMethod  {
        EMAIL("email"), FACEBOOK("facebook"), GOOGLE("google");
        private String method;

        SignInMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }

    interface View extends MVP.View {
        int RC_GOOGLE_SIGN_IN = 100;
        void signInPassword();
        void signInFacebook();
        void signInGoogle();
        void openSignUpView();
    }

    interface Presenter extends MVP.Presenter {
        void signInPassword();
        void signInFacebook();
        void signInGoogle();
    }
}
