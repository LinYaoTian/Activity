package rdc.model;

import rdc.contract.MainContract;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class MainModel implements MainContract.Model {

    private MainContract.Presenter mPresenter;

    public MainModel(MainContract.Presenter presenter){
        mPresenter = presenter;
    }



}
