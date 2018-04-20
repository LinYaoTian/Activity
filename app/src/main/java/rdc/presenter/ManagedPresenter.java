package rdc.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.ManagedActivity;
import rdc.contract.IManagedContract;
import rdc.model.ManagedModel;

import static rdc.configs.ManagedItemType.sEMPTY;
import static rdc.configs.ManagedItemType.sMANAGED;

/**
 * Created by asus on 18-4-14.
 */

public class ManagedPresenter extends BasePresenter<IManagedContract.View> implements IManagedContract.Presenter {
    private ManagedModel mModel;

    public ManagedPresenter() {
        mModel = new ManagedModel();
    }

    @Override
    public void setManagedActivity(List<Activity> list) {
        List<ManagedActivity> managedActivities = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            ManagedActivity activity = new ManagedActivity();
            Activity activity1 = list.get(i);
            activity.setImageUrl(activity1.getImage().getUrl());
            activity.setTitle(activity1.getTitle());
            activity.setSendTime(activity1.getSendtime());
            activity.setType(sMANAGED);
            activity.setId(activity1.getObjectId());
            managedActivities.add(activity);

        }
        if (list.size()==0){
            ManagedActivity activity = new ManagedActivity();
            activity.setType(sEMPTY);
            managedActivities.add(activity);
        }
        getMvpView().setManagedActivity(managedActivities);
    }

    @Override
    public void getManagedActivity() {
         mModel.getManagedActivity(this);
    }
}
