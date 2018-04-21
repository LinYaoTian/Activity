package rdc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rdc.adapter.TagsRvAdapter;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.bean.ItemTag;
import rdc.contract.TagsContract;
import rdc.listener.OnStartDragListener;
import rdc.presenter.TagsPresenter;
import rdc.widget.ItemTouchHelperCallback;

public class TagsActivity extends BaseActivity<TagsPresenter> implements OnStartDragListener,TagsContract.View {

    @BindView(R.id.toolbar_act_tags)
    Toolbar mToolbar;
    @BindView(R.id.rv_tag_list_act_tags)
    RecyclerView mRvTags;

    private static String mTagsOrder;
    private TagsRvAdapter mTagsRvAdapter;
    private List<ItemTag> mTagList;
    private ItemTouchHelper mItemTouchHelper;

    public static void actionStart(Activity activity,String tagsOrder) {
        mTagsOrder = tagsOrder;
        Intent intent = new Intent(activity, TagsActivity.class);
        activity.startActivityForResult(intent,0);
    }

    @Override
    public TagsPresenter getInstance() {
        return new TagsPresenter();
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_tags;
    }

    @Override
    protected void initData() {
        Gson gson = new Gson();
        mTagList = gson.fromJson(mTagsOrder,new TypeToken<List<ItemTag>>(){}.getType());
        mTagList.remove(0);
        mTagList.remove(0);
    }

    @Override
    protected void initView() {
        initToolbar();
        mTagsRvAdapter = new TagsRvAdapter(this);
        mTagsRvAdapter.updateData(mTagList);
        mRvTags.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvTags.setAdapter(mTagsRvAdapter);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mTagsRvAdapter));
        mItemTouchHelper.attachToRecyclerView(mRvTags);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                back();
                break;
        }
        return true;
    }

    private void back() {
        List<ItemTag> list = new ArrayList<>();
        list.add(new ItemTag("首页",true));
        list.add(new ItemTag("热门",true));
        list.addAll(mTagsRvAdapter.getDataList());
        presenter.saveTags(list);
        Intent intent = new Intent(TagsActivity.this,MainActivity.class);
        Gson gson = new Gson();
        mTagsOrder = gson.toJson(list);
        intent.putExtra("data_return",mTagsOrder);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void saveTagsError(String error) {
        showToast(error);
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
