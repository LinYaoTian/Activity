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
    }

    interface Model{
         void getManagedActivity(Presenter presenter, Organization organization);
    }


    interface Presenter {
        void setManagedActivity(List<Activity> list);
        void getManagedActivity(Organization organization);

    }
}
