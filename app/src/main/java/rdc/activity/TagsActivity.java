package rdc.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;

public class TagsActivity extends BaseActivity {

    @BindView(R.id.toolbar_act_tags)
    Toolbar mToolbar;
    @BindView(R.id.rv_tag_list_act_tags)
    RecyclerView mRvTags;

    @Override
    public BasePresenter getInstance() {
        return null;
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_tags;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToolbar();
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
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void initListener() {

    }
}
