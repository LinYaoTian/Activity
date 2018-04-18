package rdc.presenter;

import java.util.ArrayList;
import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.Organization;
import rdc.bean.User;
import rdc.contract.IConcernedContract;
import rdc.model.ConcernedModel;

import static rdc.configs.OrganizationItemType.sEMPTY;
import static rdc.configs.OrganizationItemType.sORGANIZATION;

/**
 * Created by asus on 18-4-14.
 */

public class ConcernedPresenter extends BasePresenter<IConcernedContract.View> implements IConcernedContract.Presenter {
    private ConcernedModel mModel;
    public ConcernedPresenter() {
        mModel = new ConcernedModel();
    }

    @Override
    public void setOrganization(List<User> users) {
        List<Organization> organizations  = new ArrayList<>();
        for (int i=0;i<users.size();i++){
            Organization organization = new Organization();
            User user = users.get(i);
            organization.setName(user.getNickname());
            organization.setImage(user.getUserImg());
            organization.setPhoto(user.getUserPhoto());
            organization.setTime(user.getNewSendTime());
            organization.setMessage(user.getNewsActivityTitle());
            organization.setType(sORGANIZATION);
            organization.setId(user.getObjectId());
            organization.setIntroduction(user.getIntroduction());
            organizations.add(organization);


        }
        if (organizations.size()==0){
            Organization organization = new Organization();
            organization.setType(sEMPTY);
            organizations.add(organization);

        }
        getMvpView().setOrganization(organizations);
    }

    @Override
    public void getConcernedOrganization() {
       mModel.getConcernedOrganization(this);
    }
}
