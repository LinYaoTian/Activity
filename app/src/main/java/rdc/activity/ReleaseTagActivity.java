package rdc.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rdc.avtivity.R;
import rdc.base.BaseActivity;
import rdc.base.BasePresenter;

import static rdc.activity.ReleaseActivity.RELEASE_TAG_RESULT_CODE;

/**
 * Created by WaxBerry on 2018/4/13.
 */

public class ReleaseTagActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.iv_confirm_release_tag) ImageView activity_release_tag_confirm_imageView;
    @BindView(R.id.ll_lecture_release_tag) LinearLayout activity_release_tag_lecture_linearLayout;
    @BindView(R.id.ll_contest_release_tag) LinearLayout activity_release_tag_contest_linearLayout;
    @BindView(R.id.ll_welfare_release_tag) LinearLayout activity_release_tag_welfare_linearLayout;
    @BindView(R.id.ll_outdoor_release_tag) LinearLayout activity_release_tag_outdoor_linearLayout;
    @BindView(R.id.ll_friend_release_tag) LinearLayout activity_release_tag_friend_linearLayout;
    @BindView(R.id.ll_others_release_tag) LinearLayout activity_release_tag_others_linearLayout;

    private List<LinearLayout> linearLayoutList;
    private int selectTagNum = -1;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_release_tag;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void initData() {
        linearLayoutList = new ArrayList<>();
        linearLayoutList.add(activity_release_tag_lecture_linearLayout);
        linearLayoutList.add(activity_release_tag_contest_linearLayout);
        linearLayoutList.add(activity_release_tag_welfare_linearLayout);
        linearLayoutList.add(activity_release_tag_outdoor_linearLayout);
        linearLayoutList.add(activity_release_tag_friend_linearLayout);
        linearLayoutList.add(activity_release_tag_others_linearLayout);
    }

    @OnClick({R.id.ll_lecture_release_tag, R.id.ll_contest_release_tag, R.id.ll_welfare_release_tag,
            R.id.ll_outdoor_release_tag, R.id.ll_friend_release_tag, R.id.ll_others_release_tag})
    public void onClickLinearLayout(View view) {
        for (int i = 0; i < linearLayoutList.size(); i++) {
            linearLayoutList.get(i).setBackground(null);
            if (view.getId() == linearLayoutList.get(i).getId()) {
                selectTagNum = i;
            }
        }
        view.setBackground(getResources().getDrawable(R.drawable.item_release_tag_selected_shape));
    }

    @OnClick(R.id.iv_confirm_release_tag)
    public void onClickConfirm() {
        if (selectTagNum == -1) {
            Toast.makeText(this, "请选择一个标签！" , Toast.LENGTH_SHORT).show();
        }else {
            Intent data = new Intent();
            String type = null;
            switch (selectTagNum) {
                case 0:
                    type = "讲座";
                    break;
                case 1:
                    type = "比赛";
                    break;
                case 2:
                    type = "公益";
                    break;
                case 3:
                    type = "运动";
                    break;
                case 4:
                    type = "交友";
                    break;
                case 5:
                    type = "其他";
                    break;
                default:
                    break;
            }
            data.putExtra("type", type);
            setResult(RELEASE_TAG_RESULT_CODE, data);
            finish();
        }
    }

    @Override
    public BasePresenter getInstance() {
        return null;
    }

    @Override
    protected void initView() {
        initToolBar();
    }

    @Override
    protected void initListener() {}
}
