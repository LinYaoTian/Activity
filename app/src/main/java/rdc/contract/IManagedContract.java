package rdc.contract;

import java.util.List;

import rdc.bean.Activity;
import rdc.bean.ManagedActivity;

/**
 * Created by asus on 18-4-14.
 */

public interface IManagedContract {
    interface View {
        void setManagedActivity(List<ManagedActivity> list);
    }

    interface Model{
        void getManagedActivity(Presenter presenter);
    }

    interface Presenter {
        void setManagedActivity(List<Activity> list);
        void getManagedActivity();

    }
}
