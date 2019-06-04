package com.boiko.taisa.salon.mvp.visit;

import com.boiko.taisa.salon.mvp.MVP;
import com.boiko.taisa.salon.mvp.home.Home;
import com.boiko.taisa.salon.mvp.home.HomeModel;

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
}
