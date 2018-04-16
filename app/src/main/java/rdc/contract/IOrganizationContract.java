package rdc.contract;

import java.util.List;

import rdc.bean.Organization;
import rdc.bean.User;

/**
 * Created by asus on 18-4-14.
 */

public interface IOrganizationContract {
    interface View {
        void setOrganization(List<Organization> list);
    }

    interface Model{
        void getConcernedOrganization(Presenter presenter);
    }

    interface Presenter{
        void setOrganization(List<User> users);
        void getConcernedOrganization();

    }
}
