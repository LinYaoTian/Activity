package rdc.presenter;


import rdc.base.BasePresenter;
import rdc.contract.MainContract;
import rdc.model.MainModel;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainContract.Model model;
    public MainPresenter(){
        model = new MainModel(this);
    }

}
