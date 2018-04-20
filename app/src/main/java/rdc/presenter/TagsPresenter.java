package rdc.presenter;

import java.util.List;

import rdc.base.BasePresenter;
import rdc.bean.ItemTag;
import rdc.contract.TagsContract;
import rdc.model.TagsModel;

/**
 * Created by Lin Yaotian on 2018/4/20.
 */

public class TagsPresenter extends BasePresenter<TagsContract.View> implements TagsContract.Presenter {

    private TagsContract.Model model;

    public TagsPresenter(){
        model = new TagsModel(this);
    }

    @Override
    public void saveTags(List<ItemTag> list) {
        model.saveTags(list);
    }

    @Override
    public void saveTagsError(String error) {
        if (isAttachView()){
            getMvpView().saveTagsError(error);
        }
    }
}
