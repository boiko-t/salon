package com.boiko.taisa.salon.mvp.home;

import com.boiko.taisa.salon.mvp.MVP;

public class HomePresenter implements Home.Presenter {
    private Home.View view;
    private Home.Model model;

    public HomePresenter() {
        model = HomeModel.getInstance();
        model.loadCategoryCollection();
    }

    @Override
    public void onViewAttach(MVP.View view) {
        this.view = (Home.View) view;
        model.getStateObservable()
                .subscribe(state -> {
                    if (!state.data.isEmpty()) this.view.initCategoryCollection(state.data);
                });
    }

    @Override
    public void onViewDetach() {
        this.view = null;
    }
}
