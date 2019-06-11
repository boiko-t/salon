package com.boiko.taisa.salon.mvp.visit;

import android.app.Service;

import com.boiko.taisa.salon.domain.entity.Product;
import com.boiko.taisa.salon.domain.entity.ProductUsageRecord;
import com.boiko.taisa.salon.domain.entity.SalonService;
import com.boiko.taisa.salon.domain.entity.Visit;
import com.boiko.taisa.salon.mvp.MVP;

import java.util.List;

import io.reactivex.Observable;

public interface NewVisit {
    interface View extends MVP.View {
        void initServiceCollection(List<SalonService> data);
        void initProductCollection(List<Product> data);
    }

    interface Presenter extends MVP.Presenter {
        void onClientNameUpdate(String name);
        void onServiceUpdate(SalonService service);
        void onProductListUpdate(List<ProductUsageRecord> products);
        void onVisitSubmit();
    }

    interface Model extends MVP.Model {
        void loadBaseData();
        void submitVisit();
        State getState();
        Observable<State> getStateObservable();

        class State {
            public List<SalonService> services;
            public List<Product> products;
            public Visit visit;

            public State(List<SalonService> services, List<Product> products, Visit visit) {
                this.services = services;
                this.products = products;
                this.visit = visit;
            }
        }
    }
}
