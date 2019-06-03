package com.boiko.taisa.salon.mvp.home;

import com.boiko.taisa.salon.domain.entity.Category;
import com.boiko.taisa.salon.mvp.MVP;

import java.util.List;

import io.reactivex.Observable;

public interface Home {
    interface View extends MVP.View {
        void initCategoryCollection(List<Category> data);
        void openNewVisitView();
    }

    interface Presenter extends MVP.Presenter {
        void onNewVisitClick();
    }

    interface Model extends MVP.Model {
        void loadCategoryCollection();
        Observable<State> getStateObservable();

        class State {
            public final List<Category> data;

            public State(List<Category> data) {
                this.data = data;
            }
        }
    }
}
