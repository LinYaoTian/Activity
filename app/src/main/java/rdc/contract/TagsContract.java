package rdc.contract;

import java.util.List;

import rdc.bean.ItemTag;

/**
 * Created by Lin Yaotian on 2018/4/19.
 */

public interface TagsContract {

    interface View{
        void saveTagsError(String error);
    }

    interface Presenter {
        void saveTags(List<ItemTag> list);
        void saveTagsError(String error);
    }

    interface Model{
        void saveTags(List<ItemTag> list);
    }
}
