package com.boiko.taisa.salon.mvp.visit;

import com.boiko.taisa.salon.domain.entity.Product;
import com.boiko.taisa.salon.domain.entity.SalonService;
import com.boiko.taisa.salon.mvp.MVP;

import java.util.List;

import io.reactivex.Observable;

public interface NewVisit {
    interface View extends MVP.View {
        void initServiceCollection(List<SalonService> data);
        void initProductCollection(List<Product> data);
    }

    interface Presenter extends MVP.Presenter {
    }

    interface Model extends MVP.Model {
        void loadBaseData();
        Observable<State> getStateObservable();

        class State {
            public List<SalonService> services;
            public List<Product> products;

            public State(List<SalonService> services, List<Product> products) {
                this.services = services;
                this.products = products;
            }
        }
    }
}
