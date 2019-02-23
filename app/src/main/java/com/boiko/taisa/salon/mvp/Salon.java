package com.boiko.taisa.salon.mvp;

public interface Salon {
    interface Model {
        class State {
        }
    }

    interface View {
    }

    interface Presenter {
        void onViewAttach(View view);
        void onViewDetach();
    }
}