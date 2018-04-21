package rdc.contract;

import java.util.List;

import rdc.bean.Activity;
import rdc.bean.Organization;
import rdc.bean.OrganizationActivity;

/**
 * Created by asus on 18-4-18.
 */

public interface IOrganizationDetailsContract {
    interface View{
        void setManagedActivity(List<OrganizationActivity> list);
        void setOnError();

    }

    interface Model{
         void getManagedActivity(Presenter presenter,String id);
    }


    interface Presenter {
        void setManagedActivity(List<Activity> list);
        void getManagedActivity(String id);
        void onError();

    }
}
