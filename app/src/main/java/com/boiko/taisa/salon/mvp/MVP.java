package com.boiko.taisa.salon.mvp;

public interface MVP {
    interface Model {
        class State {
        }
    }

    interface View {
    }

    interface Presenter {
        void onViewAttach(MVP.View view);
        void onViewDetach();
    }
}