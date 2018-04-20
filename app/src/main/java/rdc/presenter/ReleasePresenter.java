package rdc.presenter;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import rdc.base.BasePresenter;
import rdc.bean.User;
import rdc.contract.ReleaseContract;
import rdc.model.ReleaseModel;

/**
 * Created by WaxBerry on 2018/4/16.
 */

public class ReleasePresenter extends BasePresenter<ReleaseContract.IView> implements ReleaseContract.IPresenter{

    private String TAG = "ReleasePresenter";

    private ReleaseModel mModel;

    public ReleasePresenter() {
        mModel = new ReleaseModel(this);
    }

    @Override
    public void release() {
//        ImageUtil.compressImage(getMvpView().getImagePath(), new FileCallback() {
//            @Override
//            public void callback(boolean isSuccess, String outfile, Throwable t) {
//                if (isSuccess) {
//                    mModel.release(BmobUser.getCurrentUser(User.class), getMvpView().getUniversity(), outfile, getMvpView().getPlace(), 0,
//                            "4月17号", getMvpView().getTag(), getMvpView().getStartTime() + "~" + getMvpView().getEndTime(),
//                            getMvpView().getActivityTheme(), getMvpView().getContent(),  null);
//                }
//            }
//        });

        Log.d(TAG, "RELEASE");

        mModel.release(BmobUser.getCurrentUser(User.class), getMvpView().getUniversity(), getMvpView().getImagePath(), getMvpView().getPlace(), 0,
                getMvpView().getSendTime(), getMvpView().getTag(), getMvpView().getStartTime() + "~" + getMvpView().getEndTime(),
                getMvpView().getActivityTheme(), getMvpView().getContent(),  null, getMvpView().getExpirationDate());
    }

    @Override
    public void releaseResult(Boolean state, String message) {
        if (isAttachView()){
            if (state){
                Log.d(TAG, "STATE TRUE");
                getMvpView().onSuccess();
            }else {
                Log.d(TAG, "STATE FALSE");
                getMvpView().onError(message);
            }
            getMvpView().dismissProgressDialog();
        }
    }
}
