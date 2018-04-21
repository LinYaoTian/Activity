package rdc.presenter;

import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.OrganizationActivity;
import rdc.contract.IOrganizationDetailsContract;
import rdc.model.OrganizationDetailsModel;

import static rdc.configs.OrganizationItemType.sACTIVITY;

/**
 * Created by asus on 18-4-18.
 */

public class OrganizationDetailsPresenter extends BasePresenter<IOrganizationDetailsContract.View> implements IOrganizationDetailsContract.Presenter {

     private OrganizationDetailsModel mModel;

    public OrganizationDetailsPresenter() {
        mModel = new OrganizationDetailsModel();
    }

    /**
     * 在这里进行类型的转换
     * @param list
     */
    @Override
    public void setManagedActivity(List<Activity> list) {
        List<OrganizationActivity> organizationActivities = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            OrganizationActivity organizationActivity = new OrganizationActivity();
            Activity activity = list.get(i);
            organizationActivity.setId(activity.getObjectId());
            organizationActivity.setCoverImageUrl(activity.getImage().getUrl());
            organizationActivity.setTitle(activity.getTitle());
            organizationActivity.setTime(activity.getTime());
            organizationActivity.setLocation(activity.getPlace());
            organizationActivity.setSawNum(activity.getSawnum());
            organizationActivity.setType(sACTIVITY);
            organizationActivities.add(organizationActivity);
        }
        getMvpView().setManagedActivity(organizationActivities);
    }

    @Override
    public void getManagedActivity(String id ) {
         mModel.getManagedActivity(this,id);
    }
}
