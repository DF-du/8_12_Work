package com.example.day12;

class MainPresenter {
    private MainView view;
    private final MainModel model;

    public MainPresenter(MainView view) {
        this.view = view;
        model = new MainModel();
    }

    public void getData() {
        model.getData(new ReonClick<InfoBean>() {
            @Override
            public void cheng(InfoBean infoBean) {
                view.setData(infoBean.getRecent());
            }
        });
    }
}
