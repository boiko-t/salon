package com.boiko.taisa.salon.mvp.visit;

import android.app.Service;

import com.boiko.taisa.salon.domain.entity.ProductUsageRecord;
import com.boiko.taisa.salon.domain.entity.SalonService;
import com.boiko.taisa.salon.mvp.MVP;
import com.boiko.taisa.salon.mvp.home.Home;
import com.boiko.taisa.salon.mvp.home.HomeModel;

import java.util.List;

public class NewVisitPresenter implements NewVisit.Presenter {
    private NewVisit.View view;
    private NewVisit.Model model;

    public NewVisitPresenter() {
        model = NewVisitModel.getInstance();
        model.loadBaseData();
    }

    @Override
    public void onViewAttach(MVP.View view) {
        this.view = (NewVisit.View) view;
        model.getStateObservable()
                .subscribe(state -> {
                    if (!state.products.isEmpty() && this.view != null) {
                        this.view.initProductCollection(state.products);
                        this.view.initServiceCollection(state.services);
                    };
                });
    }

    @Override
    public void onViewDetach() {
        this.view = null;
    }

    @Override
    public void onClientNameUpdate(String name) {
        model.getState().visit.setClientName(name);
    }

    @Override
    public void onServiceUpdate(SalonService service) {
        model.getState().visit.setService(service);
    }

    @Override
    public void onProductListUpdate(List<ProductUsageRecord> products) {
        model.getState().visit.setProducts(products);
    }
}
